import java.util.Random;

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
    	public Block[] blocks; // the array of blocks
    	public short count; // the number of blocks in the set that are in use
    	public short size; // the number of blocks the set has total
        public Vector order; // a Vector used to implement the replacement policies
        public Hashmap history; // a history of the values placed in the set

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
    		order = new Vector[(int)blocksize];
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
                    if (type == Rtype.LRU)
                        order.add(order.remove(Short(tag)));
                    return WResult.HIT;
                }
            }

            // tag is not within set: determine type of miss

            // determine if it is a conflict miss
            if (history.containsKey(tag)) {
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
                    order.add(Short(tag));
                }
            }
    		
            return WResult.COMPULSORY;

    	} // write()

        /*  replace():
            
                replace one of the blocks in the set with the given address based
                on the Cache's replacement policy, and add the old block to the 
                set's address history
        */

        public void replace(Short tag) {
            if (type != RType.RAND) { // if the type is FIFO or LRU

                // pop the bottom element off of the order Vector
                Short temp = order.firstElement();
                order.remove(temp);

                // add the new value to the top of the order Vector
                order.add(Short(tag));

                // add the old value to the history
                history.put(temp.shortValue(), true);

                // replace the item in blocks with the new value
                for (int i = 0; i<size; i++) {
                    if (blocks[i].item == temp.shortValue())
                        blocks[i].item = tag;
                }

            } else { // if the type is RAND
                // select a random index
                int random = (int)(Math.random() * (size+1);

                // take the item from the block at that index and put it in the history
                history.put(blocks[random].item, true);

                // replace the old item with the new one
                blocks[random].item = tag;
            }
        } // replace()

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

class Address {
    public short tag;
    public short set;

    public Address(short t, short s) {
        tag = t;
        set = s;
    }
}