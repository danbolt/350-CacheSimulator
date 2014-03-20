public class CacheSim {

	public static void main (String[] args) {

		short policy = Short.parseShort(args[0]);
		short sets = Short.parseShort(args[1]);
		short size = Short.parseShort(args[2]);
		int trials = Integer.parseInt(args[3]);

		// System.out.println("policy: " + policy);
		// System.out.println("sets: " + sets);
		// System.out.println("size: " + size);
		// System.out.println("trials: " + trials);

		Cache myCache = new Cache(RType.LRU, 1, 1);

		if (policy == 0) {
			myCache = new Cache(RType.LRU, sets, size);
		}
		else if (policy == 1) {
			myCache = new Cache(RType.FIFO, sets, size);
		}
		else if (policy == 2) {
			myCache = new Cache(RType.RAND, sets, size);
		}
		else {
			System.out.println("0: LRU, 1: FIFO, 2: RAND");
			return;
		}

		int hit = 0;
		int capacity = 0;
		int compulsory = 0;
		int conflict = 0;
		int unknown = 0;

		for (int i = 0; i < trials; i++) {
			Address address = generateAddress(size,sets);
			WResult t = myCache.write(address);
			switch (t) {
				case HIT:
					hit++; // update integer counter for cache hits
					break;
				case COMPULSORY:
					compulsory++; // update integer counter for compulsory misses
					break;
				case CAPACITY:
					capacity++; // update integer counter for capacity misses
					break;
				case CONFLICT:
					conflict++; // update integer counter for comnflict misses
					break;
				default:
					unknown++; // this shouldn't happen
					break;
			} // switch
		} // for

		System.out.println(hit + " " + compulsory + " " + capacity + " " + conflict + " " + (hit+compulsory+capacity+conflict));
		// switch (policy) {
		// 	case 0:
		// 		System.out.println("LRU");
		// 		break;
		// 	case 1:
		// 		System.out.println("FIFO");
		// 		break;
		// 	case 2:
		// 		System.out.println("RAND");
		// 		break;
		// } // switch
	}

	public static Address generateAddress(short size, short sets) {
		short si = (short)(Math.random() * (size));
		short se = (short)(Math.random() * (sets));
		return new Address(si,se);
	}
}