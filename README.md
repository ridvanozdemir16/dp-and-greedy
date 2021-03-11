Analysis of dynamic programming and greedy approach for some scenerios, and calculation of Big-O notations.

# Scenario

Part-1

Assume that you are the owner of a car company. Your company has enough employees to produce ‘p’ cars for each month. However, the number of the demand for the cars differs from
month to month. You should design a sales plan for the next ‘x’ months. Consider ‘i’ is the index of each month (i=1,…,x) and mi is the demand for i th month. If your company needs to produce more than ‘p’ cars for a month, you can hire some interns, paying ‘d’ TL per car for that month. Moreover, if your company keeps any unsold car at the end the month, you should pay a ‘garage cost’. The garage cost will be calculated by the function G(j), where, 

    G(j) < G(j+1) , j > 0
    G(j) > 0 
    
To understand the problem: Your current employees can produce p=5 cars in a month, for the three month (x=3), m[ ] = { 7, 3, 6 } and G(1) = 5, G(2)= 7, G(3)=10, G(4)=12, G(5)=13, etc... and d=5 TL.

For the first month, your employees can produce 5 cars, however, the demand is 7. You can hire interns with the cost of d*(7-5) TL. 

For the second month, the demand is 3, will you produce 5 and keep 2 cars in the garage for the next months for not paying intern costs? Or will you just produce 3 cars?

Part-2

Besides, you must invest the payment that earned from your sales. Cost of each car is ‘B’ TL and you get half of the price at the beginning of the month and the rest will be taken at the end of the month. You have offers from ‘c’ different investment companies. Each investment company offers different rate for each month. At the end of each month, you can change your investment company by paying a taxes at a rate ‘t’ of your invested money or continue with the same investment company without paying any taxes. 

To understand the problem: Consider B=100 TL, c=3 -> C1 ={ 10, 8, 6 }, C2 ={ 8, 10, 6 }, C3 ={ 6, 8, 10 } and t is 2%.

For the first month, the demand was 7, so half of it is 7*100/2 = 350 TL. Your company can invest 350 TL at the begining of 1st month. For only one month, C1 seems as the best option. 10% of 350 = 35 TL, at the end of the month, it will be 385 TL in total. Should you change C1 with C2 or C3 ?

Hint: Income at the end of 1st month = the second half of 1st month (350 TL) + the first half of 2ndmonth (3*100/2=150 TL) = 350 + 150 = 400 TL. 

If you change the investment company you must pay 2% of 385, then add 400 on your money for the second month. If you don’t change the company 385+400 will be invested in the same invesment company.

There is a design of a dynamic programming approach that minimizes the production costs and maximizes the profit from invesments at the end of the ‘x’ months. In order to evaluate the dynamic programming method, you are also expected to develop a greedy approach for these plans.

Some variables are given in ‘month_demand.txt’ , ‘garage_cost.txt’ and ‘invesment.txt’ files. 

The rest of variables (‘x’, ‘p’, ‘d’, ‘B’, ‘c’ and ‘t’) will be default at the beginning of the code.

The program prints the difference between “total profit from invesments” (Part-2) and “the cost comes from interns and garage costs” (Part-1) at the end of xth months
