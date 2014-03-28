class Tester {
	
	public static void main(String[] args) {
		Cache myCache = new Cache(RType.LRU, 2, 4);
		Address[] myAddress = new Address[12];
		myAddress[0] = new Address(1,0);
		myAddress[1] = new Address(2,0);
		myAddress[2] = new Address(2,0);
		myAddress[3] = new Address(3,0);
		myAddress[4] = new Address(4,0);
		myAddress[5] = new Address(1,0);
		myAddress[6] = new Address(2,1);
		myAddress[7] = new Address(3,1);
		myAddress[8] = new Address(0,1);
		myAddress[9] = new Address(2,1);
		myAddress[10] = new Address(4,1);
		myAddress[11] = new Address(3,1);
		

		for (int i = 0; i < myAddress.length; i++) {
			System.out.println("ADDRESS " + i  + ":\nset: " + myAddress[i].set + "\ntag: " + myAddress[i].tag);
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
			System.out.print("\n");
		} // for
	}
}