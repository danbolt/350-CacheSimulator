import java.util.*;

enum RType {
    LRU, FIFO, RAND
}

enum WResult {
	HIT, COMPULSORY, CAPACITY, CONFLICT
}

class Cache {

    protected final RType type; // the replacement type used
    private short numSets; // the number of sets/associativity
    private short numBlocks; // the number of memory blocks/size
    private Set[] sets; // this is the array of sets
    private int tally;

    private class Set {
    	public Block[] blocks; // the array of blocks
    	public short count; // the number of blocks in the set that are in use
    	public short size; // the number of blocks the set has total
        public Vector<Short> order; // a Vector used to implement the replacement policies
        public HashMap<Short, Boolean> history; // a history of the values placed in the set

    	private class Block {
    		public short value;

    		public Block(short val) {
    			value = val;
    		}

    	} // block

    	public Set(short blocksize) {
    		size = blocksize;
    		count = 0;
    		blocks = new Block[blocksize];
    		order = new Vector<Short>((int)blocksize);
            history = new HashMap<Short, Boolean>();
    	} // Set constructor

        /*  write():
            
                writes an address into the set based on its tag, returns a WResult enum
                so the caller can tell whether it was a hit or which kind of miss it was
        */

    	public WResult write(short tag) {

            //System.out.println("count: " + count + "\nsize: " + size);
            
            // check for tag within set
            for (int i = 0; i < size; i++) {
                if (blocks[i] != null && blocks[i].value == tag) {
                    if (type == RType.LRU) {
                        Short temp = new Short(tag);
                        order.remove(temp);
                        order.add(temp);
                    }
                    return WResult.HIT;
                }
            }

            // tag is not within set: determine type of miss

            // determine if it is a conflict miss
            if (history.containsKey(new Short(tag))) {
                replace(tag);
                return WResult.CONFLICT;
            }

            // if it is not a conflict miss and the set is full, it is a capacity miss
            if (count == size) {
                replace(tag);
                return WResult.COMPULSORY;
            }

            // tag is not within set, find empty block to replace
            for (int i = 0; i < size; i++) {
                if (blocks[i] == null) {
                    blocks[i] = new Block(tag);
                    order.add(new Short(tag));
                    count++;
                    return WResult.COMPULSORY;
                }
            }
    		
            return null;

    	} // write()

        /*  replace():
            
                replace one of the blocks in the set with the given address based
                on the Cache's replacement policy, and add the old block to the 
                set's address history
        */

        public void replace(short tag) {
            if (type != RType.RAND) { // if the type is FIFO or LRU

                // pop the bottom element off of the order Vector
                Short temp = order.firstElement();
                order.remove(temp);

                // add the new value to the top of the order Vector
                order.add(new Short(tag));

                // add the old value to the history
                history.put(temp, new Boolean(true));

                // replace the item in blocks with the new value
                for (int i = 0; i<size; i++) {
                    if (blocks[i].value == temp.shortValue())
                        blocks[i].value = tag;
                }

            } else { // if the type is RAND
                // select a random index
                int random = (int)(Math.random() * (size));

                // take the item from the block at that index and put it in the history
                history.put(new Short(blocks[random].value), new Boolean(true));

                // replace the old item with the new one
                blocks[random].value = tag;
            }

        } // replace()

    } // Set

    public Cache(RType t, short s, short b) {
    	type = t;
    	numSets = s;
    	numBlocks = b;
    	short blocksize = (short)(b/s);
    	sets = new Set[numSets];

    	for (int i = 0; i<numSets; i++)
    		sets[i] = new Set(blocksize);

    } // Cache constructor

    public Cache(RType t, int s, int b) {
        type = t;
        numSets = (short)s;
        numBlocks = (short)b;
        short blocksize = (short)(b/s);
        sets = new Set[numSets];
        tally = 0;

        for (int i = 0; i<numSets; i++)
            sets[i] = new Set(blocksize);

    } // Cache constructor

    /*  write():
            
            writes a given address into the cache, returns a WResult to indicate the result
    */
    public WResult write(Address address) {
        WResult ret = sets[address.set].write(address.tag);

        if (ret == WResult.CONFLICT) {
            if (tally >= numBlocks*numSets) {
                return WResult.CAPACITY;
            }
        }
        tally++;

        return ret;
    } // write()

} // cache

class Address {
    public short tag;
    public short set;

    public Address(short t, short s) {
        tag = t;
        set = s;
    }

    public Address(int t, int s) {
        tag = (short)t;
        set = (short)s;
    }
}