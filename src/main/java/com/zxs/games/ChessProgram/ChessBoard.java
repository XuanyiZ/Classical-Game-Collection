package com.zxs.games.ChessProgram;
import java.util.Stack;
/**
 * ChessBoard represents a game configuration of chess,
 * it uses a 8*8 array to record piece information at each grid
 * It also has a int turn to denote which player goes this turn
 * turn = 0 denotes the player in the up position and 1 denotes down position
 */
public class ChessBoard {
    public int turn;
    public int winner = -1;
    public Boolean ended = false;
    public ChessPiece[][] chessBoard;
    public Stack<moveStats> moveStack = new Stack<moveStats>();
    public Boolean standard = true;
    public ChessBoard(){
        this(0);
    }
    public ChessBoard(int turn, Boolean standard) {
        this.standard = standard;
        this.turn = turn;
        chessBoard = new ChessPiece[8][8];
        for(int row =0; row < 8 ; row++){
            for(int col = 0; col < 8 ;col++){
                chessBoard[row][col] = null;
            }
        }
        //construct new chessboard
        for(int col=0;col<8;col++){
            chessBoard[1][col] = new Pawn(0);
            chessBoard[6][col] = new Pawn(1);
        }
        for(int player = 0;player < 2;player++){
            int row = player == 0 ? 0 : 7;
            chessBoard[row][0] = new Rook(player);
            chessBoard[row][1] = new Knight(player);
            if (standard)
                chessBoard[row][2] = new Bishop(player);
            else
                chessBoard[row][2] = new BishopKnight(player);
            chessBoard[row][3] = new Queen(player);
            chessBoard[row][4] = new King(player);
            chessBoard[row][5] = new Bishop(player);
            chessBoard[row][6] = new Knight(player);
            if (standard)
                chessBoard[row][7] = new Rook(player);
            else
                chessBoard[row][7] = new RookKnight(player);
        }
    }
    public ChessBoard(int turn){
        this(turn, true);
    }

    /**
     * convert the board to a string representation,  mainly used for debugging
     * @return
     */
    public String toString(){
        String resultBoard= "";
        for(int row = 0; row < 8; row ++){
            for(int col = 0; col< 8;col++){
                if (chessBoard[row][col] == null)
                    resultBoard+='.';
                else{
                    resultBoard+= chessBoard[row][col];
                }
            }
            resultBoard+="\n";
        }
        return resultBoard;
    }

    /**
     * empty the chessBoard
     */
    public void emptyChessBoard(){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8 ; col++){
                chessBoard[row][col] = null;
            }
        }
    }

   /**
    * movePiece at (startRow, startCol) to (endRow, endCol)
    * return code:
    * @return  0: failed, 1: success and moved piece, 2: player0 wins after moving 3: player 1 wins after moving, 4: draws after moving
    * 
    * 
    */ 
   public int movePiece(int startRow, int startCol, int endRow, int endCol){
        if (canMove(startRow, startCol, endRow, endCol)){
            addToMoveStack(startRow,startCol,endRow,endCol);
            turn = 1 -  turn;
            chessBoard[endRow][endCol] = chessBoard[startRow][startCol];
            chessBoard[startRow][startCol] = null;
            int result = isEnding();
            if (result == 1)
                return result;
            ended = true;
            if (result == 2){
                winner = 0; // black
            } else if (result ==3) {
                winner = 1; // white
            } else if (result == 4){
                winner = -1; // denotes draw
            }
            return result;
        }else{
            System.out.println("Cannot move piece!");
            return 0;
        }

    }

    /**
      * check if is ending, (if current turn player can make a legal move)
      * return code:
      * 1: not ending , 2: player0 wins, 3: player 1 wins, 4: draws
      */
    public int  isEnding(){
        if (hasLegalMove(turn)){
            return 1;
        }
        if (isChecked(turn)){ // turn player loses
            if (turn  == 0){
                return 3;
            }else
                return 2;
        }else{
            return 4;
        }

    }

    /**
     * if player can make a legal move, which means player can make a move and king is not checked after the move
     */
    public Boolean hasLegalMove(int player){
        for(int row =  0; row < 8; row ++){
            for(int col = 0; col < 8;col++){
                ChessPiece  curPiece = chessBoard[row][col];
                if (curPiece != null && curPiece.getPlayer() == player){
                    for(int m = 0; m < 8;m++){
                        for(int n = 0;n < 8 ;n++){
                            if (curPiece.canMove(row, col, m , n, this)){
                                ChessPiece destinationPiece = chessBoard[m][n];
                                chessBoard[row][col] = null;
                                chessBoard[m][n] = curPiece;
                                Boolean isChecked = isChecked(player);
                                chessBoard[row][col]  = curPiece; // undo move
                                chessBoard[m][n] = destinationPiece;
                                if (!isChecked)
                                    return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /** 
     *return if player's king is being checked by the opponent
     */
    public Boolean isChecked(int player){
        int myKingRow = -1 , myKingCol = -1;
        for(int row = 0; row < 8 ;row++){
            for(int col = 0; col < 8; col++) {
                if (chessBoard[row][col] != null && chessBoard[row][col].getPlayer()==player
                        && chessBoard[row][col] instanceof King ){
                    myKingRow = row;
                    myKingCol = col;
                }
            }
        }
        for(int row = 0; row < 8 ;row++){
            for(int col = 0; col < 8; col++){
                if (chessBoard[row][col] != null && chessBoard[row][col].getPlayer()!=player){
                    if ( chessBoard[row][col].canMove(row, col, myKingRow, myKingCol, this)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * if turn player can move piece from () to ()
     * 1. check in boundary
     * 2. check valid move for the piece
     * 3. check not being checked
     */
    public Boolean canMove(int startRow, int startCol, int endRow, int endCol){
        if (ended) return false;
        if (!inBoundary(startRow, startCol, endRow, endCol))
            return false;
        ChessPiece startPiece = chessBoard[startRow][startCol];
        if (startPiece == null)
            return false;
        if (startPiece.getPlayer() != turn ){
            return false;
        }
        if (startRow == endRow && startCol == endCol){
            return false;
        }
        if ( !startPiece.canMove(startRow, startCol, endRow, endCol, this) ){
            return false;
        }
        ChessPiece endPiece = chessBoard[endRow][endCol];
        chessBoard[endRow][endCol] = startPiece;
        chessBoard[startRow][startCol] = null;
        Boolean isChecked = isChecked(turn);
        chessBoard[endRow][endCol] = endPiece;
        chessBoard[startRow][startCol] = startPiece;
        if (isChecked) {
            return false;
        }
        return true;
    }

    /**
     * @param row
     * @param col
     * @return true if the piece at (row,col) belongs to the current player
     */
    public Boolean isCurrentPlayerPiece(int row, int col){
        if (row<0 || row >=8 || col <0 || col >=8)
            return false;
        if (chessBoard[row][col] == null)
            return false;
        return chessBoard[row][col].getPlayer() == turn;
    }

    /**
     * check if all the coordinates are in boundary
     */
    public Boolean inBoundary(int startRow, int startCol, int endRow, int endCol ){
        if (startRow <0 || startRow >= 8)
            return false;
        if (startCol <0 || startCol >= 8)
            return false;
        if (endRow <0 || endRow >= 8)
            return false;
        if (endCol <0 || endCol >= 8)
            return false;
        return true;
    }

    /**
     * add a move from (startRow,startCol) to (endRow,endCol) to the moveStack
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     */
    public void addToMoveStack(int startRow,int startCol,int endRow,int endCol){
        moveStats move = new moveStats(startRow,startCol,endRow,endCol,chessBoard[startRow][startCol], chessBoard[endRow][endCol]);
        moveStack.push(move);
    }

    /**
     * undo the last move. Won't undo if the game is ended or hasn't started
     * @return information about last move, including two coordinates and two piece information
     */
    public moveStats undo(){
        if (ended) return null;
        if (moveStack.empty()) return null;
        moveStats lastMove = moveStack.pop();
        chessBoard[lastMove.startRow][lastMove.startCol] = lastMove.startPiece;
        chessBoard[lastMove.endRow][lastMove.endCol] = lastMove.endPiece;
        turn = 1 - turn;
        return lastMove;
    }
}
