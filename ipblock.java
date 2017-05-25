public class ipblock{
	
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

	public static void main(String[] args){
		if(args.length < 2){
			System.out.println("Not enough arguments.");
			System.out.println("Usage: ipblock BLOCK ADDR");
			return;
		}	

		String subnet = args[0];
		String[] ipBlock = subnet.split("/");
		if(ipBlock.length != 2){
			System.out.println("Subnet not formatted correctly.");
			return;
		}

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

		String addr = args[1];
		long block = 0L, check = 0L;

		try{
			block = convert(ipBlock[0]);
			check = convert(addr);
		}
		catch(IllegalArgumentException e){
			System.out.println("Error parsing addresses: " + e.getMessage());
			return;
		}

		// System.out.printf("%32s\n", Long.toString(block, 2));
		// System.out.printf("%32s\n", Long.toString(check, 2));

		long bitmask = (1L << prefix) - 1;
		bitmask <<= (32 - prefix);
		// System.out.printf("%32s\n", Long.toString(bitmask, 2));

		bitmask &= block;
		bitmask ^= check;
		bitmask >>= (32 - prefix);

		if(bitmask == 0)
			System.out.println("in block");
		else
			System.out.println("not in block");
	}

}