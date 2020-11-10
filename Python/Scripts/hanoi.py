"""
    Start      End     Offset
    1          3       2
    3          1       2
    1          2       3
    2          1       3
    2          3       1
    3          2       1

Offset = 6 - Start - End
6 = number of towers * 2
"""

def hanoi(discs, start = 1, end = 3):
    if discs:
        # From de current to the offset position
        hanoi(discs - 1, start, 6 - start - end)
        print('Move the disc {} from the tower {} to tower {}'.format(
            discs, start, end
        ))
        # From the offset to End position
        hanoi(discs - 1, 6 - start - end, end)


hanoi(discs=4)
