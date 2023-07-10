import functools

def coinChange(coins, amount):
    coins.sort(reverse=True)

    result = []
    count = 0

    for coin in coins:
        count = int(amount/coin)
        result = result + [coin]*count
        amount %= coin
        
    print(result)    
    return result

if __name__ == '__main__':
    coinChange([5, 2, 10, 1], 17); #[10, 5, 2]
    coinChange([8, 1, 9, 4], 13); #[9, 4]
    coinChange([10, 1, 5, 2], 0); #[] => no match, returns an empty array
    coinChange([1, 3, 4, 5, 6], 2); #[1,1]
    coinChange([1, 6, 7, 10, 11], 35); #[11, 11, 11, 1, 1]
