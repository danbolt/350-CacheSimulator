public enum RType {
    LRU, FIFO, RAND
}

public enum WResult {
	HIT, COMPULSORY, CAPACITY, CONFLICT
}

class Cache {

    protected final RType type; // the replacement type used
    private int numSets; // the number of sets/associativity
    private int numBlocks; // the number of memory blocks/size
    private Set[] sets; // this is the array of sets

    private class Set {
    	public Block[] blocks;
    	public short count;
    	public short size;
    	public boolean accessed;

    	private class Block {
    		public short value;

    		public Block(val) {
    			value = val;
    		}

    		public Block() {
    			value = null;
    		}
    	}

    	public Set(short blocksize) {
    		size = blocksize;
    		count = 0;
    		blocks = new Block[blocksize];
    		order = new short[blocksize];
    		accessed = false;
    	}

    	public WResult write(Address address) {
    		if (accessed) {
    			if (blocks[address.offset].value == null || address == blocks[address.offset].value) {
    				return HIT;
    			} else {
                    replace(blocks[address.offset],address);
                    return CONFLICT;
                }
                return CAPACITY;
                
    		} else {
    			return COMPULSORY;
    		}

    	}

        public void replace(Block old, Address address) {

        }

    }

    public Cache(RType t, int sets, int blocks) {
    	type = t;
    	numSets = sets;
    	numBlocks = blocks;
    	short blocksize = blocks/sets;
    	sets = new Set[sets];
    	for (int i = 0; i<sets; i++)
    		sets[i] = new Set[blocksize];

    }

    public WResult write(short tag, short set, short offset) {
    	return sets[address%sets].write(address);
    }

}