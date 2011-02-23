# -*- coding: iso-8859-1 -*-
class Move:
  def __init__(self, game, id, fromFile, fromRank, toFile, toRank, promoteType):
    self.game = game
    self.id = id
    self.fromFile = fromFile
    self.fromRank = fromRank
    self.toFile = toFile
    self.toRank = toRank
    self.promoteType = promoteType

  def toList(self):
    value = [
      self.id,
      self.fromFile,
      self.fromRank,
      self.toFile,
      self.toRank,
      self.promoteType,
      ]
    return value

  def nextTurn(self):
    pass



class Piece:
  def __init__(self, game, id, owner, file, rank, hasMoved, type):
    self.game = game
    self.id = id
    self.owner = owner
    self.file = file
    self.rank = rank
    self.hasMoved = hasMoved
    self.type = type

  def toList(self):
    value = [
      self.id,
      self.owner,
      self.file,
      self.rank,
      self.hasMoved,
      self.type,
      ]
    return value

  def nextTurn(self):
    pass
  
  #this bit does moves without consequences for failure
  def move(self,file,rank,type):
    if self.game.moves is 0:
      return "You already moved"
    self.game.moves = 0 
    self.game.addObject(Move(self.game,self.game.nextid,self.file,self.rank,file,rank,type))
    makeMoveVal = self.makeMove(file,rank,type)
    if makeMoveVal is not True:
      self.game.declareWinner(self.game.players[self.game.playerID^1],makeMoveVal)
    printableState = ""
    theBoard = self.createBoard()
    for i in range(0,8):
      for j in range(0,8):
        if theBoard[i][j] is None:
          printableState += "."
        else:
          printableState += chr(theBoard[i][j].type)
      printableState += "\n"
    printableState += "\n"
    print printableState 
    if self.game.inCheck(self.owner^1):
      if self.game.inCheckmate(self.owner^1):
        self.game.declareWinner(self.game.players[self.owner],("White" if self.owner is 0 else "Black") + " Wins by Checkmate")
    return True
  
  
  def makeMove(self,file,rank,type):  
    oldfile = self.file
    oldrank = self.rank
    oldtrank = rank
    oldtfile = file
    targetPiece = None
    board = self.createBoard()
    isLegal = True
    verifyVal = self.verifyMove(file,rank,type)
    if verifyVal is True:
      if self.hasMoved is 0:
        self.hasMoved = 1
        if self.type is ord('P') and abs(self.rank-rank) is 2:
          self.hasMoved = 2
      for piece in [i for i in self.game.objects.values() if isinstance(i, Piece)] :
        if piece.rank == rank and piece.file == file:
          targetPiece = piece
          targetPiece.rank = -1
          targetPiece.file = -1
        if piece.id is not self.id and piece.hasMoved is 2:
          piece.hasMoved = 1
      if self.file-file is 2 and self.type is ord('K'):
        rook = board[self.rank-1][0]
        self.file = file+1
        if self.game.inCheck(self.owner):
          return ("White" if self.game.playerID is 0 else "Black") + " Attempted to Castle through Check!"
        rook.file = file+1
      if self.type is ord('K') and self.file-file is -2:
        rook = board[self.rank-1][7]
        self.file = file-1
        if self.game.inCheck(self.owner):
          return ("White" if self.game.playerID is 0 else "Black") + " Attempted to Castle through Check!"
        rook.file = file-1
      if self.type == ord('P') and rank is 1 + 7 * (self.owner^1):
        self.type = type
      if self.type == ord('P') and board[rank-1][file-1] is None and self.file is not file:
        #EnPassant Code  
        for piece in [i for i in self.game.objects.values() if isinstance(i,Piece)]:
          if piece.file == file and abs(piece.rank - rank) < 2:
            targetPiece = piece
            oldtrank = targetPiece.rank
            oldtfile = targetPiece.file
            targetPiece.rank = -1
            targetPiece.file = -1
      self.rank = rank
      self.file = file
      if self.game.inCheck(self.owner):
        self.rank = oldrank
        self.file = oldfile
        if targetPiece is not None:
          targetPiece.rank = oldtrank
          targetPiece.file = oldtfile
        return ("White" if self.game.playerID is 0 else "Black") + " Attempted to make a Move which ended with itself in check!"
    else:
      return "type: "+chr(self.type)+" Rank: "+`self.rank`+" to "+`rank`+", File: "+`self.file`+" to "+`file`+" "+verifyVal
    
    self.game.removeObject(targetPiece)
    return True

  def verifyMove(self,file,rank,type):

    canMove = True
    #Creates a board with all of the pieces in it
    board = self.createBoard()
    if file < 1 or file > 8 or rank < 1 or rank > 8:
      return ("White" if self.game.playerID is 0 else "Black") + " Attempted to make a move for which the Destination was off of the chess board!"
    elif rank == self.rank and file == self.file:
      return ("White" if self.game.playerID is 0 else "Black") + " Attempted to make a move which did not move any piece!"
    elif self.owner != self.game.playerID:
        return ("White" if self.game.playerID is 0 else "Black") + " Tried to move a piece that belonged to " + ("White" if self.game.playerID is 1 else "Black")
    #Checks the final position. If it is an enemy piece the move is valid. If it is our own then it is invalid
    elif board[rank-1][file-1] is not None and board[rank-1][file-1].owner == self.owner:
      return ("White" if self.game.playerID is 0 else "Black") + " Attempted to take its own Piece!"
      
    #Move logic for a pawn
    elif self.type == ord('P'):
      
      if board[rank-1][file-1] is None:
        #En Passant code
        if self.file is not file:
          #allows an en-passant only if there is a pawn that just moved two below where it is moving if the player is white, above where it is moving if the player is black, and that that pawn is an enemy
          if not (board[rank+2*(self.owner-1)][file-1] is not None and board[rank+2*(self.owner-1)][file-1].type is ord('P') and board[rank+2*(self.owner-1)][file-1].owner is not self.owner and rank - self.rank + 2*self.owner is 1 and board[rank+2*(self.owner-1)][file-1].hasMoved is 2):
            return  ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a pawn into an empty square which was not directly ahead when said move was not a legal En Passant"
        #checks for double move after the first    
        elif rank - self.rank + 4*self.owner is 2: 
          if self.hasMoved is 0:
            canMove = self.checkRank(rank)
          else:
            return ("White" if self.game.playerID is 0 else "Black") + " Attempted to double move a pawn after it had already moved"
        #checks for regular move
        elif rank - self.rank + 2*self.owner is not 1:
          return ("White" if self.game.playerID is 0 else "Black") + " Attempted to make an illegal move with a Pawn"
      elif abs(self.file-file) is not 1 or (rank - self.rank + 2*self.owner is not 1):
        return ("White" if self.game.playerID is 0 else "Black") + "Attempted to take a piece with a Pawn in a manner that Pawns cannot"
      if rank is 1 + 7 * (self.owner^1):
        if type is not ord('R') and type is not ord('N') and type is not ord('B') and type is not ord('Q'):
          return ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a Pawn into the far row without providing a valid Pawn Upgrade Identifier, instead providing " + chr(type)
    #Move logic for a rook
    elif self.type == ord('R'):
      if file == self.file:
        canMove = self.checkRank(rank)
      elif rank == self.rank:
        canMove = self.checkFile(file)
      else:
        return ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a Rook in something other than a straight line!" 


    elif self.type == ord('N'):
      if not ((abs(self.rank-rank) is 2 and abs(self.file-file) is 1) or (abs(self.rank-rank) is 1 and abs(self.file-file) is 2)):
        return ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a Knight In something other than an L shape"
        
    elif self.type == ord('B'):
      canMove = self.checkDiagonal(rank,file)
    
    elif self.type == ord('Q'):
      if file == self.file:
        canMove = self.checkRank(rank)
      elif rank == self.rank:
        canMove = self.checkFile(file)
      else:
        canMove = self.checkDiagonal(rank,file)

    elif self.type == ord('K'):
      #logic for castling
      if (not (self.hasMoved == 1)) and (self.rank == rank) and (abs(self.file-file) is 2):
        if file > self.file:
          for i in range(self.file, file):
            if board[rank-1][i] is not None:
              return ("White" if self.game.playerID is 0 else "Black") + " Attempted to castle, but there were pieces in the way!"
          if board[rank-1][7] is not None:
            if board[rank-1][7].owner is not self.owner or board[rank-1][7].type is not ord('R') or board[rank-1][7].hasMoved is not 0:
              return ("White" if self.game.playerID is 0 else "Black") + " Attempted to castle, but the Rook in question was either missing or had already moved"
        else:
          for i in range(file-2, self.file):
            if board[rank-1][i] is not None:
              return ("White" if self.game.playerID is 0 else "Black") + " Attempted to castle, but there were pieces in the way"
            if board[rank-1][0] is not None:
              if board[rank-1][0].owner is not self.owner or board[rank-1][0].type is not ord('R') or board[rank-1][0].hasMoved is not 0:
                return ("White" if self.game.playerID is 0 else "Black") + " Attempted to castle, but the Rook in question was either missing or had already moved"
     #normal move logic
      elif abs(self.file - file) > 1:
        return ("White" if self.game.playerID is 0 else "Black") + " Attempted to move their King farther than it could move"
      elif abs(self.rank - rank) > 1:
        return ("White" if self.game.playerID is 0 else "Black") + " Attempted to move their King farther than it could move"
              
    return canMove


  #Checks for pieces in between the moving piece's current rank and the desired rank
  #Assuming no change in file
  def checkRank(self, rank):
    canMove = True
    board = self.createBoard()
    #If the piece is moving upwards
    if rank > self.rank:
      for i in range(self.rank , rank-1):
        if board[i][self.file-1] is not None:
          canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a piece, but there were other pieces in the way"
    #If the piece is moving downwards
    elif self.rank > rank:
      for i in range(rank,self.rank - 1):
        if board[i][self.file-1] is not None:
          canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a piece, but there were other pieces in the way"
   
    #Will return True if there is no pieces in between this piece and the desired location
    return canMove

  #Checks for pieces in between the moving piece's file and the desired file
  #Assuming no change in rank
  def checkFile(self,file):
    canMove = True
    board = self.createBoard()
    #If the piece is moving to the right
    if file > self.file:
      for i in range(self.file, file-1):
        if board[self.rank-1][i] is not None:
          canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a piece, but there were other pieces in the way"
    #If the piece is moving to the left
    elif self.file > file:
      for i in range(file, self.file - 1):
        if board[self.rank-1][i] is not None:
          canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a piece, but there were other pieces in the way"

    return canMove


  #Not sure if any of this works right now... Was going fast.
  def checkDiagonal(self,rank,file):
    canMove = True
    board = self.createBoard()
    #checks that it is in fact a diagonal move
    if abs(self.rank-rank) is not abs(self.file-file):
      canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a non Knight piece in a move which was neither straight nor diagonal"
    #If the piece is moving to the right
    elif file > self.file:
      for i in range(self.file, file-1):
        #Moving to Up-Right
        if rank > self.rank:
          if board[self.rank + i - self.file][i] is not None:
            canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a piece diagonally, but there were other pieces in the way"
        #Moving to Down-Right
        elif rank < self.rank:
          if board[self.rank - 2 - i + self.file][i] is not None:
            canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a piece diagonally, but there were other pieces in the way"
    #If the piece is moving to the left
    elif file < self.file:
      for i in range(file, self.file - 1):
        #Moving to Up-Left
        if rank > self.rank:
          if board[rank - i + file -2][i] is not None:
            canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a piece diagonally, but there were other pieces in the way at rank " + chr(rank - i + file - 1) + " file " + chr(i+1)
        #Moving to Down-Left
        elif rank < self.rank:
          if board[rank + i - file][i] is not None:
            canMove = ("White" if self.game.playerID is 0 else "Black") + " Attempted to move a piece diagonally, but there were other pieces in the way"
    return canMove
    
  #creates a board containing all of the pieces
  def createBoard(self) :
    board = [ [None] * 8 for i in xrange(8) ]
    for object in self.game.objects.values():
      if isinstance(object, Piece):
        board[object.rank-1][object.file-1] = object
    return board




