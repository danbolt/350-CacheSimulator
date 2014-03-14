public enum RType {
    LRU, FIFO, RAND
}

public enum WResult {
	HIT, COMPULSORY, CAPACITY, CONFLICT
}

class Cache {

    protected final RType type; // the replacement type used
    private short numSets; // the number of sets/associativity
    private short numBlocks; // the number of memory blocks/size
    private Set[] sets; // this is the array of sets

    private class Set {
    	public Block[] blocks;
    	public short count;
    	public short size;
        public Hashmap history;

    	private class Block {
    		public short value;

    		public Block(val) {
    			value = val;
    		}

    		public Block() {
    			value = null;
    		}
    	} // block

    	public Set(short blocksize) {
    		size = blocksize;
    		count = 0;
    		blocks = new Block[blocksize];
    		order = new short[blocksize];
            history = new HashMap(short, boolean);
    	} // Set constructor

        /*  write():
            
                writes an address into the set based on its tag, returns a WResult enum
                so the caller can tell whether it was a hit or which kind of miss it was
        */

    	public WResult write(short tag) {

            // check for tag within set
            for (int i = 0; i < size; i++) {
                if (blocks[i].value = tag) {
                    return WResult.HIT;
                }
            }

            // tag is not within set: determine type of miss

            // determine if it is a conflict miss
            if (determineConflict(address)) {
                replace(tag);
                return WResult.CONFLICT;
            }

            // if it is not a conflict miss and the set is full, it is a capacity miss
            if (count = size) {
                replace(tag);
                return WResult.CAPACITY;
            }

            // tag is not within set, find empty block to replace
            for (int i = 0; i < size; i++) {
                if (blocks[i] = null) {
                    blocks[i].value = tag;
                }
            }
    		
            return WResult.COMPULSORY;

    	} // write()

        /*  replace():
            
                replace one of the blocks in the set with the given address based
                on the Cache's replacement policy, and add the old block to the 
                set's address history
        */

        public void replace(Address address) {

        } // replace()

        /*  determineConflict():

            check against history of addresses for set for the address passed in, 
            if history contains the address, then return true, else return false
        */
        public boolean determineConflict(Address address) {

        }

    } // Set

    public Cache(RType t, short sets, short blocks) {
    	type = t;
    	numSets = sets;
    	numBlocks = blocks;
    	short blocksize = blocks/sets;
    	sets = new Set[sets];
    	for (int i = 0; i<sets; i++)
    		sets[i] = new Set[blocksize];

    } // Cache constructor

    /*  write():
            
            writes a given address into the cache, returns a WResult to indicate the result
    */
    public WResult write(Address address) {
    	return sets[set].write(address.tag);
    } // write()

} // cache