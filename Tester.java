class Tester {
	
	public static void main(String[] args) {
		Cache myCache = new Cache(RType.LRU, 8, 16);
		Address[] myAddress = new Address[5];
		myAddress[0] = new Address(1,0);
		myAddress[1] = new Address(2,0);
		myAddress[2] = new Address(1,0);
		myAddress[3] = new Address(3,0);
		myAddress[4] = new Address(2,0);

		for (int i = 0; i < 5; i++) {
			switch (myCache.write(myAddress[i])) {
				case HIT:
					System.out.println("HIT");
					break;
				case COMPULSORY:
					System.out.println("COMPULSORY MISS");
					break;
				case CAPACITY:
					System.out.println("CAPACITY MISS");
					break;
				case CONFLICT:
					System.out.println("CONFLICT MISS");
					break;
				default:
					System.out.println("lolwut");
					break;
			} // switch
		} // for
	}
}