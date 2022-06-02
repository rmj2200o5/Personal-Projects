from random import randint, sample
from colorama import Fore

# Settings color changes
FR = Fore.RESET
FLW = Fore.LIGHTWHITE_EX
print(FR, end="")
caves = range(1, 21)
x = sample(caves, 6)
# Randomly assigns starting locations
playerLoc = x[0]
wumpusLoc = x[1]
pit1Loc = x[2]
pit2Loc = x[3]
bat1Loc = x[4]
bat2Loc = x[5]

arrowsLeft = 5
connectedRooms = [[2, 5, 8], [1, 3, 10], [2, 4, 12], [3, 5, 14], [1, 4, 6], [5, 7, 15], [6, 8, 17], [1, 7, 9],
                  [8, 10, 18], [2, 9, 11], [10, 12, 19], [3, 11, 13], [12, 14, 20], [4, 13, 15], [6, 14, 16],
                  [15, 17, 20], [7, 16, 18], [9, 17, 19], [11, 18, 20], [13, 16, 19]]
isRunning = True
userWin = False


# Returns an integer between the two integer parameters
# Uses the input string as a prompt
def confirmInput(small, big, string):
    correctInput = False
    num = -1
    while not correctInput:
        try:
            num = int(input(f"{string} ({small} to {big}): "))
            # checks if in range
            if small <= num <= big:
                return num
            else:
                print(f"You must enter an integer between {small} and {big} inclusive!\n")
        except ValueError:
            print(f"You must enter an integer between {small} and {big} inclusive!\n")


# Prints the game map showing the player current location in blue text
def printMap():
    locations = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                 "19", "20"]
    locations[playerLoc - 1] = Fore.LIGHTBLUE_EX + str(playerLoc)

    print(f"       {FLW}{locations[17]}{FR}-----------------{FLW}{locations[18]}{FR}")
    print(f"      /\\    ----{FLW}{locations[9]}{FR}---     /\\ ")
    print(f"     /  \\ /     |     \\  /  \\ ")
    print(f"    /    {FLW}{locations[8]}{FR}    --{FLW}{locations[1]}{FR}--     {FLW}{locations[10]}{FR}   \\ ")
    print(f"   /    /    /      \\    \\    \\ ")
    print(
        f"  /    {FLW}{locations[7]}{FR}----{FLW}{locations[0]}{FR}        {FLW}{locations[2]}{FR}---{FLW}{locations[11]}{FR}    \\")
    print(f"  \\     \\    \\      /    /     /")
    print(
        f"   {FLW}{locations[16]}{FR}----{FLW}{locations[6]}{FR}    {FLW}{locations[4]}{FR}----{FLW}{locations[3]}{FR}    {FLW}{locations[12]}{FR}---{FLW}{locations[19]}{FR}")
    print(f"     \\    \\  /      \\  /     /")
    print(
        f"      \\     {FLW}{locations[5]}{FR}---{FLW}{locations[14]}{FR}---{FLW}{locations[13]}{FR}     /")
    print(f"       \\        |          /")
    print(f"        --------{FLW}{locations[15]}{FR}---------")


# Creates a 25% chance that the wumpus randomly moves to another room
def moveWumpus():
    print("The wumpus has awaken!")
    global wumpusLoc
    chance = randint(1, 100)
    if chance > 25:
        print("The wumpus is moving!")
        wumpusLoc = connectedRooms[wumpusLoc - 1][randint(1, len(connectedRooms[wumpusLoc - 1])) - 1]
        print()
        # Ends game if wumpus moves into the players room
        if wumpusLoc == playerLoc:
            global isRunning
            isRunning = False
            print("The wumpus moved into your room! You have been eaten")


def movePlayer():
    global playerLoc
    connected = False
    while not connected:
        # Have user enter a room number
        moveto = confirmInput(1, 20, 'What room number do you want to move to?')
        print()
        # Tell user if that room is not connected
        if moveto in connectedRooms[playerLoc - 1]:
            playerLoc = moveto
            connected = True
        else:
            print('There is no tunnel connecting that room!\n')
    # Once they move,  check if the new room has a bottomless pit or super bat
    # If there is call their respective method to move use or end the game.
    if playerLoc == pit1Loc or playerLoc == pit2Loc:
        pitRoom()
    if playerLoc == bat1Loc or playerLoc == bat2Loc:
        batRoom()
    # Also check if the new room has a Wumpus
    # If Wumpus is in the room,  the Wumpus wakes up
    if playerLoc == wumpusLoc:
        moveWumpus()
        # game if wumpus doesn't leave the room
        if playerLoc == wumpusLoc:
            print("The wumpus didn't move! You have been eaten!")
            print()
            global isRunning
            isRunning = False


# Player dies if they walk into a pit room
def pitRoom():
    global isRunning
    print("AAAAAAAAAAAAAAAAAHHHHHHHHHHH!!!!! You have died from falling\n")
    isRunning = False


# Bat moves player to another room
def batRoom():
    print("A bat moves you")
    global playerLoc
    # generates a random room
    playerLoc = randint(1, 20)
    # checks if player was dropped in a room with a wumpus, bat, or pit
    if playerLoc == wumpusLoc:
        moveWumpus()
    if playerLoc == pit1Loc or playerLoc == pit2Loc:
        pitRoom()
    if playerLoc == bat1Loc or playerLoc == bat2Loc:
        batRoom()


def shootArrow():
    # have them enter how far they want to shoot (1-5 rooms)
    distance = confirmInput(1, 5, "Shoot through how many rooms?")
    # Have user enter an arrow path
    path = []
    for i in range(distance):
        path.append(confirmInput(1, 20, f"Room #{i + 1} of path"))
    print()
    # check to make sure input rooms are connected
    path.insert(0, playerLoc)
    randomShot = False
    for i in range(len(path) - 1):
        if not path[i] in connectedRooms[path[i + 1] - 1]:
            print("Your arrow path is not a valid one... the arrow will travel randomly")
            randomShot = True
            break
    # If not,  generate a random path, without repeating rooms
    if randomShot:
        path = [playerLoc]
    usedRooms = [playerLoc]
    if randomShot:
        for i in range(distance):
            roomIndex = randint(1, len(connectedRooms[path[i] - 1])) - 1
            while connectedRooms[path[i] - 1][roomIndex] in usedRooms:
                roomIndex = randint(1, len(connectedRooms[path[i] - 1])) - 1
            usedRooms.append(connectedRooms[path[i] - 1][roomIndex])
            path.append(connectedRooms[path[i] - 1][roomIndex])
    # print out new path if random path was made
    path.pop(0)
    sPath = [str(num) for num in path]
    stringPath = "-".join(sPath)
    if randomShot:
        print(f"Your random path is as follows: {stringPath}\n")

    # check if the Wumpus is in one of the rooms the arrow went through
    global isRunning
    global userWin
    if wumpusLoc in path:
        # If so,  end the game. Player Wins
        print("Wumpus has been hit")
        userWin = True
        isRunning = False
    else:
        # If the Wumpus isn't hit,  it wakes up
        # Call the wumpus move method
        print("Your arrow didn't hit anything!\n")
        moveWumpus()
    return arrowsLeft - 1


def giveWarnings():
    # Give Warnings:
    # 1 away from Wumpus
    skipLine = False
    if wumpusLoc in connectedRooms[playerLoc - 1]:
        print('I smell a Wumpus.')
        skipLine = True
    # 1 from pit
    if pit1Loc in connectedRooms[playerLoc - 1] or pit2Loc in connectedRooms[playerLoc - 1]:
        print('I feel a draft.')
        skipLine = True
    # 1 away from bats
    if bat1Loc in connectedRooms[playerLoc - 1] or bat2Loc in connectedRooms[playerLoc - 1]:
        print('Bats nearby.')
        skipLine = True
    # print an extra line if a warning was printed
    if skipLine:
        print()


# Make a while Loop
while isRunning:
    # Call giveWarnings()
    giveWarnings()
    # Ask the user to move or shoot an arrow. 1=move 2=shoot
    choice = confirmInput(1, 2,
                          "Would you like to move or shoot an arrow?\n1. Move\n2. Shoot Arrow\nPick a choice from")
    # Call printMap() so user can see nearby rooms
    printMap()
    print(f"You are located at room {Fore.LIGHTBLUE_EX + str(playerLoc) + FR}")
    # If player wants to move,  call movePlayer()
    if choice == 1:
        movePlayer()
    # If they choose to shoot,  Call shootArrow(): arrowsLeft = shootArrows()
    if choice == 2:
        arrowsLeft = shootArrow()
    # If the wumpus isn't hit,  and the user has no arrows left (start with 5) the User loses
    if arrowsLeft == 0:
        print("You have run out of arrows!\n")
        isRunning = False
# check if player won
if userWin:
    print("Congratulations! You have won the game!")
else:
    print("Oh no! You have lost the game!")
    print("Better luck next time!")
