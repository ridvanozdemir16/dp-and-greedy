import java.io.*;

public class Ridvan_Ozdemir_2017510086 {
	static int[] months_demands = new int[50];
	static int[] garage_costs = new int[310];
	static int[][] investments = new int[50][6];

		// READING METHODS \\
	
	public static void readDemands(String fileName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		String line;
		BufferedReader br = new BufferedReader(fileReader);
		int k = 0;
		while ((line = br.readLine()) != null) {
			if (k != 0) {
				String[] splitted = line.split("\t");
				months_demands[k - 1] = Integer.parseInt(splitted[1]);
			}
			k++;
		}
	}

	public static void readGarageCosts(String fileName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		String line;
		BufferedReader br = new BufferedReader(fileReader);
		int k = 0;
		while ((line = br.readLine()) != null) {
			if (k != 0) {
				String[] splitted = line.split("\t");
				garage_costs[k - 1] = Integer.parseInt(splitted[1]);
			}
			k++;
		}
	}

	public static void readInvestments(String fileName) throws IOException {
		FileReader fileReader = new FileReader(fileName);
		String line;
		BufferedReader br = new BufferedReader(fileReader);
		int k = 0;
		while ((line = br.readLine()) != null) {
			if (k != 0) {
				String[] splitted = line.split("\t");
				for (int i = 0; i < splitted.length - 1; i++) {
					investments[k - 1][i] = Integer.parseInt(splitted[i + 1]);
				}
			}
			k++;
		}
	}
	
		// READING METHODS \\
	
		// DYNAMIC PROGRAMING METHODS \\
	
	public static double DP_Profit(int p, int d, int x, double t, int B, int c) {
		//variables to store previous months informations.
		int companies[] = new int[x]; //storing company selections
		double profits[][] = new double[x][c];  //storing company selections
		
		//variables that make reading more easier 
		double firstHalfMoney = months_demands[0] * B / 2; double secondHalfMoney = 0; double investmentMoney = 0; double profit = firstHalfMoney;
		
		//Before the main loop,I calculated the first half of 1st month profit and first company's income.
		for (int i = 0; i < c; i++) {
			profits[0][i] = profit + profit * ((double) investments[0][i] / 100);//store the money coming from companies.
			if (profit * investments[0][i] / 100 > investmentMoney) {
				investmentMoney = profit * (double) investments[0][i] / 100;
				companies[0] = i;//store the first month company.
			}
		}
		profit += (double) investmentMoney;
		//main loop
		for (int i = 0; i < x; i++) {
			double maxProfit = 0;
			secondHalfMoney = months_demands[i] * B / 2;
			if (i != x - 1) {//if we are in the final month, we don't need choose any company for next month.
				profit += (int) secondHalfMoney;
				for (int j = 0; j < c; j++) {
					double tempProfit = profits[i][j];//temp variable for profit of the previous month
					for (int k = 0; k < c; k++) {
						double newProfit = 0;//temp variable to compare profits that are coming from companies
						if (j != k) {//if the company is different for next month
							double taxes = (int) (tempProfit * (double) (t / 100));//calculating tax
							newProfit = (tempProfit - taxes + secondHalfMoney + months_demands[i + 1] * B / 2);//money - taxes + the second half of 1st month  + the first half of 2nd month
							newProfit += (newProfit * ((double) investments[i + 1][k] / 100));//adding investment money of next month.

						} else {//if the company is the same for next month
							newProfit = (tempProfit + secondHalfMoney + months_demands[i + 1] * B / 2);//money + the second half of 1st month  + the first half of 2nd month
							newProfit += (newProfit * ((double) investments[i + 1][k] / 100));//adding investment money of next month.
						}
						if (newProfit > profits[i + 1][k]) {//comparing profits.
							profits[i + 1][k] = (int) newProfit;
							if (newProfit > maxProfit) {
								maxProfit = (int) newProfit;
								companies[i] = j;
								companies[i + 1] = k;
							}
						}
					}
				}
				profit = (int) maxProfit;//set the profit as maximum according to company incomes
			}
		}
		System.out.println("DP-Profit: "+profit+" + "+secondHalfMoney);
		return profit + secondHalfMoney;
	}

	public static int DP_Cost(int p, int d, int x, double t, int B, int c) {
		int totalCost = Integer.MAX_VALUE;
		int R = 0;//variable to keep total demand
		for (int i = 0; i < x; i++) {
			R += months_demands[i];
		}
		int[][] costs = new int[x + 1][R + 1];//matrix to keep costs for each month
		costs[0][0] = 0;
		//initialize the costs matrix for the 0. month(probability of remain car from the zeroth month).
		for (int i = 1; i < R + 1; i++) {
			costs[0][i] = garage_costs[i - 1];
		}
		//main loop
		for (int i = 1; i < x + 1; i++) {
			int demand = months_demands[i - 1];
			totalCost = Integer.MAX_VALUE;
			for (int j = 0; j < R + 1; j++) {
				int carsFromPreviousMonth = j;
				int totalCar = carsFromPreviousMonth + p;
				for (int k = 0; k < R + 1; k++) {
					int costOfPreviousMonth = costs[i - 1][j];
					int newCost = costOfPreviousMonth;
					// conditions ( hire intern or store cars in garage or remaining cars from previous months) 
					if (totalCar == demand + k) {//k -> k is the cars that will put into garage
						if (k != 0 && totalCar - demand >= k)
							newCost += garage_costs[k - 1];
					} else if (totalCar > demand + k) {
						if (k != 0)
							newCost += garage_costs[k - 1];
					} else if (totalCar < demand + k) {
						if (k != 0) {
							newCost += (demand + k - totalCar) * d;
							newCost += garage_costs[k - 1];
						} else {
							newCost += (demand + k - totalCar) * d;
						}
					}
					// conditions ( hire intern or store cars in garage or remaining cars from previous months) 
					
					if (j == 0) {//dont need compare for first scan.
						costs[i][k] = newCost;
					} else {
						if (newCost < costs[i][k]) {
							costs[i][k] = newCost;
						}
					}
					if (i == x) {//final cost is total cost.
						if (newCost < totalCost)
							totalCost = newCost;
					}
				}
			}
		}
		System.out.println("DP-Cost: "+totalCost);
		return totalCost;
	}

		// DYNAMIC PROGRAMING METHODS \\
	
		// GREEDY METHODS \\
	
	public static double Greedy_Profit(int p, int d, int x, double t, int B, int c) {
		//variables that make reading more easier 
		double firstHalfMoney = 0; double secondHalfMoney = 0; double investmentMoney = 0; double profit = 0;
		int previousInvestmentCompany = 0; int investmentCompany = 0; int investmentRate = 0;
		
		//Before the main loop,I calculated the first half of 1st month profit and first company's income.
		for (int i = 0; i < c; i++) {
			if ((months_demands[0] * B / 2) * investments[0][i] / 100 > investmentMoney) {
				investmentRate = investments[0][i];
				investmentCompany = i;
				investmentMoney = (months_demands[0] * B / 2) * investments[0][i] / 100;
			}
		}
		//money that is coming from company.
		investmentMoney = months_demands[0] * B / 2 * (double) investmentRate / 100;
		for (int i = 0; i < x; i++) {
			previousInvestmentCompany = investmentCompany;//storing the company of the previous month.
			firstHalfMoney = months_demands[i] * B / 2;// first half of month
			profit += firstHalfMoney + (int) investmentMoney;
			investmentRate = 0;
			investmentMoney = 0;
			secondHalfMoney = (double) months_demands[i] * B / 2;//second half of month.
			for (int j = 0; j < c; j++) {
				double tempInvestmentMoney = 0;//temp variable to compare profits that are coming from companies
				if (previousInvestmentCompany != j) {//if the company and previous month's company are not same
					double taxes = (int) (profit * (double) (t / 100));
					double tempProf = profit - taxes;
					tempInvestmentMoney = tempProf + ((tempProf + secondHalfMoney + months_demands[i + 1] * B / 2) * (double) investments[i + 1][j] / 100);
				} else {//if the company and previous month's company are different
					tempInvestmentMoney = profit + ((profit + secondHalfMoney + months_demands[i + 1] * B / 2) * ((double) investments[i + 1][j]) / 100);
				}
				if (tempInvestmentMoney > investmentMoney) {//
					investmentCompany = j;
					investmentRate = investments[i + 1][j];
					investmentMoney = tempInvestmentMoney;
				}
			}
			//if we are not in the final month or company has change, tax will be charged
			if (previousInvestmentCompany != investmentCompany && i != x - 1) {
				double taxes = (int) (profit * (double) (t / 100));
				profit -= taxes;
			}
			investmentMoney = (profit + secondHalfMoney + months_demands[i + 1] * B / 2) * investmentRate / 100;//calculating money that is coming from company.
			if (i != x - 1)
				profit += (int) secondHalfMoney;
		}
		System.out.println("Greedy-Profit: "+profit+" + "+(double)secondHalfMoney);
		return profit +  secondHalfMoney;
	}

	public static int Greedy_Cost(int p, int d, int x, double t, int B, int c) {
		int cost = 0;
		for (int i = 0; i < x; i++) {
			int demand = months_demands[i];
			if (p < demand)
				cost += (demand - p) * d;
		}
		System.out.println("Greedy-Cost: "+cost);
		return cost;
	}
		
		// GREEDY METHODS \\

	public static void main(String[] args) throws IOException {
		readDemands("month_demand.txt");
		readInvestments("investment.txt");
		readGarageCosts("garage_cost.txt");

		int p = 2; int d = 2; int x = 25; int t = 2; int B = 100; int c = 6;
		
		System.out.println("DP-Result: " + (DP_Profit(p, d, x, t, B, c)-DP_Cost(p, d, x, t, B, c)));
		System.out.println("-------------------------------------");
		System.out.println("Greedy-Result: " + (Greedy_Profit(p, d, x, t, B, c)-Greedy_Cost(p, d, x, t, B, c)));
	}
}
