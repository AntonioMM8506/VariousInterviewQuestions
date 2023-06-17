def pascalTriangle(n):
    arr = []
    
    def fact(x):
        if(x==0):
            return 1
        return x*fact(x-1)
    
    if(n==0):
        arr.append(0)
    else:
        m = fact(n)
        arr.append(1)

        if(n>1):
            for i in range(1,n):
                p = fact(i)*fact(n-i)
                arr.append(int(m/p))

        arr.append(1)
    
    print(arr)
    return arr
#End of pascalTriangle

#main----------------------------------------------------
if __name__ == '__main__':
    pascalTriangle(0)
    pascalTriangle(1)
    pascalTriangle(2)
    pascalTriangle(3)
    pascalTriangle(4)
    pascalTriangle(5)
