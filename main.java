int tileSize = 20; // Size of each tile
int columns, rows; // Number of columns and rows in the grid
int snakeLength = 1;
int[] snakeX, snakeY; // Arrays to store snake's segments' positions
int foodX, foodY; // Position of the food
int directionX = 1, directionY = 0; // Initial direction (right)
boolean gameStarted = false;
boolean gameOver = false;

void setup() {
  fullScreen();
  columns = width / tileSize;
  rows = height / tileSize;
  snakeX = new int[width * height];
  snakeY = new int[width * height];
  snakeX[0] = 0; // Initial snake position
  snakeY[0] = 0;
  foodX = round(random(columns - 1)) * tileSize; // Initial food position
  foodY = round(random(rows - 1)) * tileSize;
}

void draw() {
  background(220, 149, 172);
  if (gameStarted) {
    if (!gameOver) {
      moveSnake();
      checkCollisions();
      checkFood();
      drawSnake();
      drawFood();
    } else {
      drawGameOverScreen();
    }
  } else {
    drawStartButton();
  }
}

void drawStartButton() {
  fill(0);
  rect(width / 2 - 50, height / 2 - 25, 100, 50);
  fill(255);
  textAlign(CENTER, CENTER);
  text("Start", width / 2, height / 2);
}

void drawGameOverScreen() {
  fill(0);
  textSize(24);
  textAlign(CENTER, CENTER);
  text("Game Over", width / 2, height / 2 - 50);
  fill(255);
  rect(width / 2 - 50, height / 2, 100, 50);
  fill(0);
  text("Restart", width / 2, height / 2 + 25);
}

void keyPressed() {
  if (!gameStarted && key == ' ' && !gameOver) {
    gameStarted = true;
  }
  if (gameOver && key == ' ') {
    resetGame();
  }
  if (keyCode == UP && directionY != 1) {
    directionX = 0;
    directionY = -1;
  } else if (keyCode == DOWN && directionY != -1) {
    directionX = 0;
    directionY = 1;
  } else if (keyCode == LEFT && directionX != 1) {
    directionX = -1;
    directionY = 0;
  } else if (keyCode == RIGHT && directionX != -1) {
    directionX = 1;
    directionY = 0;
  }
}

void resetGame() {
  snakeLength = 1;
  snakeX[0] = 0;
  snakeY[0] = 0;
  directionX = 1;
  directionY = 0;
  foodX = round(random(columns - 1)) * tileSize;
  foodY = round(random(rows - 1)) * tileSize;
  gameOver = false;
}

void moveSnake() {
  for (int i = snakeLength - 1; i > 0; i--) {
    snakeX[i] = snakeX[i - 1];
    snakeY[i] = snakeY[i - 1];
  }
  snakeX[0] += directionX * tileSize;
  snakeY[0] += directionY * tileSize;
}

void checkCollisions() {
  if (snakeX[0] < 0 || snakeX[0] >= width || snakeY[0] < 0 || snakeY[0] >= height) {
    gameOver = true;
  }
  for (int i = 1; i < snakeLength; i++) {
    if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
      gameOver = true;
    }
  }
}

void checkFood() {
  if (snakeX[0] == foodX && snakeY[0] == foodY) {
    snakeLength++;
    foodX = round(random(columns - 1)) * tileSize;
    foodY = round(random(rows - 1)) * tileSize;
  }
}

void drawSnake() {
  for (int i = 0; i < snakeLength; i++) {
    fill(0);
    rect(snakeX[i], snakeY[i], tileSize, tileSize);
  }
}

void drawFood() {
  fill(255, 0, 0);
  ellipse(foodX + tileSize / 2, foodY + tileSize / 2, tileSize, tileSize);
}
