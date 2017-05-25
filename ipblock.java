/**
	*
	*The ipblock class provides both testing for membership within a subnet
	*as well as enumerating all ip addresses within a subnet
	*
	*@author	Brian Kim
*/
public class ipblock{
	
	/*
		The convert function converts a IPv4 String into the corresponding
		long representation.
		-Throws an Illegal Argument Exception if the input is malformed
	*/
	public static long convert(String s) throws IllegalArgumentException{
		String[] blocks = s.split("\\.");
		if(blocks.length != 4)
			throw new IllegalArgumentException("Address must contain three periods.");
		long output = 0;
		for(int i = 0; i < 4; i++){
			//check if all four numbers are valid integers
			try{
				int b = Integer.parseInt(blocks[i]);
				if(b < 0 || b > 255)
					throw new IllegalArgumentException("Number out of range (0 <= b <= 255)");
				output *= (1L << 8);
				output += b;
			}
			catch(NumberFormatException e){
				throw new IllegalArgumentException("Expected four integers in address.");
			}
		}
		return output;
	}

	/*
	printAddress takes in a long integer and prints out the
	corresponding IPv4 address
	*/
	public static void printAddress(long addr){
		long bitmask = (1L << 8) - 1;
		StringBuilder sb = new StringBuilder();
		//checks in blocks of 8 bits
		for(int i = 0; i < 4; i++){
			long part = addr & bitmask;
			sb.insert(0,part);
			sb.insert(0, ".");
			addr >>= 8;
		}
		//can be optimized by storing everything in a buffer
		//and then printing at the end
		System.out.println(sb.substring(1));
	}

	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("Not enough arguments.");
			System.out.println("Usage: ipblock BLOCK ADDR");
			return;
		}	

		//parse the subnet
		String subnet = args[0];
		String[] ipBlock = subnet.split("/");
		if(ipBlock.length != 2){
			System.out.println("Subnet not formatted correctly.");
			return;
		}

		//extracts prefix
		int prefix = 0;
		try{
			prefix = Integer.parseInt(ipBlock[1]);
			if(prefix < 0 || prefix > 32){
				System.out.println("Prefix size is not valid.");
				return;
			}
		}
		catch(NumberFormatException e){
			System.out.println("Prefix size must be an integer.");
			return;
		}

		//extracts the subnet starting address/block
		long block = 0L;
		try{
			block = convert(ipBlock[0]);
		}
		catch(IllegalArgumentException e){
			System.out.println("Error parsing addresses: " + e.getMessage());
			return;
		}
		
		//printing the address
		if(args.length == 1){
			long bitmask = (1L << prefix) - 1;
			bitmask <<= (32 - prefix);
			bitmask &= block; //extracts the significant bits
			for(int i = 0; i < (1L << (32 - prefix)); i++)
				printAddress(bitmask + i);
			return;
		}

		//verification with a second argument
		String addr = args[1];
		long check = 0L;
		try{
			check = convert(addr);
		}
		catch(IllegalArgumentException e){
			System.out.println("Error parsing addresses: " + e.getMessage());
			return;
		}

		//extract the significant bits 
		//the XOR of the bitmask and address should be zero
		long bitmask = (1L << prefix) - 1;
		bitmask <<= (32 - prefix);
		bitmask &= block;
		bitmask ^= check;
		bitmask >>= (32 - prefix);

		if(bitmask == 0)
			System.out.println("in block");
		else
			System.out.println("not in block");
	}

}