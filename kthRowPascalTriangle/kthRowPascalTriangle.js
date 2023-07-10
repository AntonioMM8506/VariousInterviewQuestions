function pascalTriangle(n){
    let arr = []

    //Declare a function inside a function
    var fact = (function(x){
        if(x==0){
            return 1
        }
        return x * fact(x-1);
    });

    //First index
    if(n==0){
        arr.push(0);
    }else{
        let m = fact(n);
        //The 1 from the beginning, its power its always 0
        arr.push(1);

        //For all the other positions, just have to use the formula to calculate the value of the position
        //Position = m! / (n!*(m-n)!)
        //Where "m" is the number of column and "n" the given position  
        if(n>1){
            for(let i=1; i<=n-1; i++){
                let p = fact(i)*fact(n-i);
                arr.push(m/p)
            }
        }
        //The 1 from the ending, its power its always 0
        arr.push(1);
    }

    return arr
}//End of pascalTriangle

//Test cases ------------------------------------------------------------s
console.log(pascalTriangle(0)); //[0]
console.log(pascalTriangle(1)); //[1,1]
console.log(pascalTriangle(2)); //[1,2,1]
console.log(pascalTriangle(3)); //[1,3,3,1]
console.log(pascalTriangle(4)); //[1,4,6,4,1]
console.log(pascalTriangle(5)); //[1,5,10,10,5,1]
