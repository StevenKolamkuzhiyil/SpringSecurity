const gameStatus = {in_progress: 'IN PROGRESS', win: 'YOU WIN', lose: 'YOU LOSE', tie: 'TIE', error: 'Error'};
const seedX = {mark:'X',color:'#007bff'};
const seedO = {mark:'O',color:'#dc3545'};
const playerName = $("meta[name='userRef']").attr("content");
var playerSeed = {};
var cpuSeed = {};
var board = createBoard();
var turnCount = 0;
var difficulty;

$(document).ready(function () {
  $('#seed-X').click(function () {
    startGame(seedX, seedO);
  });
  $('#seed-O').click(function () {
    startGame(seedO, seedX);
    randomMove();
    console.log(board);
  });

  $('#ttt-board td').on('click', function() {
    var status = turn(this, playerSeed, playerName);
    if (status === null) return;
    console.log(board);
    if (hasGameTerminated(status)) return;

    if (difficulty === '2') {
      status = bestMove(9);
    } else if (difficulty === '1') {
      status = Math.round(Math.random()) === 0 ? bestMove(0) : bestMove(9);
    } else {
      status = bestMove(0);
    }
    if (status === gameStatus.win) {
      status = gameStatus.lose;
    }
    console.log(board);
    hasGameTerminated(status)
  });

  $('#reset-btn, #rematch-btn').click(function() {
    resetBoard();
  });

});

function hasGameTerminated(status) {
  if (status !== gameStatus.in_progress) {
    console.log(status);
    $('#choose-seed h3').text(status);
    if (!$('#choose-seed p').hasClass('d-none')) {
      $('#choose-seed p').addClass('d-none');
    }
    if (!$('#difficulty').hasClass('d-none')) {
      $('#difficulty').addClass('d-none');
    }
    $('#rematch-btn').removeClass('d-none');
    $('#choose-seed').toggleClass('hide');
    return true;
  }
  return false;
}

function startGame(seedP, seedC) {
  playerSeed = seedP;
  cpuSeed = seedC;
  if (!$('#choose-seed').hasClass('hide')) {
    $('#choose-seed').addClass('hide');
  }
  difficulty = $('#difficulty select').children("option:selected").val();
  console.log(difficulty);
}

function createBoard() {
  var board = new Array(3);
  for (var i = 0; i < board.length; i++) {
      board[i] = new Array(3);
      board[i].fill(null);
  }
  return board;
}

function resetBoard() {
  for (var i = 1; i <= 9; i++) {
    $('#board' + i).text('');
    $('#board' + i).removeAttr('style');
  }
  $('#choose-seed h3').text('Select Seed');
  $('#choose-seed p').removeClass('d-none');
  $('#difficulty').removeClass('d-none');
  if(!$('#rematch-btn').hasClass('d-none')){
    $('#rematch-btn').addClass('d-none');
  }
  $('#playerMoves tbody').empty();
  $('#choose-seed').removeClass('hide');
  board = createBoard();
  turnCount = 0;
}

function setSeed(element, seed) {
  var row_index = parseInt($(element).parent().index('tr'));
  var col_index = parseInt($(element).index('tr:eq('+row_index+') td'));
  if (board[row_index][col_index] === null) {
    board[row_index][col_index] = seed.mark;
    element.innerText = seed.mark;
    element.style = 'color:'+seed.color+';';
    return { x: row_index, y: col_index};
  }
  return null;
}

function tableAddCurrentMove(playerName, mark, move) {
  var row = '<tr>'+
              '<th scope="row">'+turnCount+'</th>'+
              '<td>'+playerName+'</td>'+
              '<td>'+mark+'</td>'+
              '<td>('+move.x+', '+move.y+')</td>'+
            '</tr>';

  $("#playerMoves tbody").append(row);
}

function turn(element, seed, playerName) {
  var move = setSeed(element, seed);
  if (move === null) return null;
  turnCount++;
  tableAddCurrentMove(playerName, seed.mark, move);

  if (checkWin(board, move)) {
    return gameStatus.win;
  } else if (turnCount >= 9) {
    return gameStatus.tie;
  }
  return gameStatus.in_progress;
}

function checkWin(gameboard, move) {
  return gameboard[move.x][move.y] !== null &&
         (horizontalWin(gameboard, move.x, move.y) ||
         verticalWin(gameboard, move.x, move.y) ||
         diagonalWin(gameboard, move.x, move.y) ||
         antiDiagonalWin(gameboard, move.x, move.y));
}

function horizontalWin(gameboard, x, y) {
  var row = gameboard[x];
  return gameboard[x][y] && row.every(val => val === gameboard[x][y]);
}
function verticalWin(gameboard, x, y) {
  var col = gameboard.map(function(row,index) { return row[y]; });
  return gameboard[x][y] && col.every(val => val === gameboard[x][y]);
}
function diagonalWin(gameboard, x, y) {
  if (x !== y) return false;
  var diag = gameboard.map(function(row,index) { return  row[j++]; }, j=0);
  return gameboard[x][y] && diag.every(val => val === gameboard[x][y]);
}
function antiDiagonalWin(gameboard, x, y) {
  var j = gameboard.length - 1;
  if (x+y !== j) return false;
  var diag = gameboard.map(function(row,index) { return  row[j--]; });
  return gameboard[x][y] && diag.every(val => val === gameboard[x][y]);
}

function randomMove() {
  var moves = generateMoves(board);
  if (typeof moves === 'undefined' || moves.length <= 0) return gameStatus.error;

  var move = moves[Math.floor(Math.random() * moves.length)];
  var element = document.getElementById('ttt-board').rows[move.x].cells[move.y];
  return turn(element, cpuSeed, "CPU");
}

function bestMove(limit) {
  var bestM = minimax(board, turnCount, cpuSeed.mark, limit, true, -Infinity, Infinity);
  var move = bestM.pos;
  if (move !== null) {
    var element = document.getElementById('ttt-board').rows[move.x].cells[move.y];
    return turn(element, cpuSeed, "CPU");
  } else {
    return randomMove();
  }
}

function minimax(gameboard, tCount, mark, limit, isMaximizing, alpha, beta) {
  var bestM = {score: (isMaximizing ? -Infinity : Infinity), pos: null};
  var moves = generateMoves(gameboard);
  for (var i = 0; i < moves.length; i++) {
    var move = moves[i];
    gameboard[move.x][move.y] = mark;
    tCount++;

    var eval = evalMove(gameboard, move, tCount, isMaximizing);
    if (limit > 0 && eval === null) {
      var evalM = minimax(gameboard, tCount, toggleMark(mark), limit - 1, !isMaximizing, alpha, beta);
      eval = evalM.score;
    }

    gameboard[move.x][move.y] = null;
    tCount--;

    if (eval === null) continue;
    if (isMaximizing && eval > bestM.score) {
        bestM = {score: eval, pos: move};
        alpha = Math.max(alpha, eval);
    } else if (!isMaximizing && eval < bestM.score) {
        bestM = {score: eval, pos: move};
        beta = Math.min(beta, eval);
    }

    if (beta <= alpha) {
      break;
    }
  }

  return bestM;
}

function evalMove(gameboard, move, tCount, isMaximizing) {
  if (checkWin(gameboard, move) && isMaximizing) {
    return 1 * (10 - tCount);
  } else if (checkWin(gameboard, move) && !isMaximizing) {
    return -1 * (10 - tCount);
  } else if (tCount >= 9) {
    return 0;
  }
  return null;
}

function toggleMark(mark) {
  return mark === seedX.mark ? seedO.mark : seedX.mark;
}

function generateMoves(gameboard) {
  var moves = [];
  for (var i = 0; i < gameboard.length; i++) {
    for (var j = 0; j < gameboard[i].length; j++) {
      if (gameboard[i][j] === null) {
        moves.push({x: i, y: j});
      }
    }
  }
  return moves;
}