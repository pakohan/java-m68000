package m68000;



public final class Arguments {
	private String prefix;
	private String postfix;
	private boolean twoparts;
	
	public Arguments(final String arg) {
		if (arg.contains(",")) {
			String[] split = new String[2];
			split = arg.split(",");
			this.prefix = split[0];
			this.postfix = split[1];
			this.twoparts = true;
		} else {
			this.prefix = arg;
			this.postfix = "";
			this.twoparts = false;
		}
	}
	
	public final String getArg() {
		return this.prefix;
	}
	
	public final String getPostfix() {
		return this.postfix;
	}
	
	public final boolean hastwoparts() {
		return this.twoparts;
	}
	
	@Override
	public final String toString() {
		if (this.twoparts) {
			return this.prefix + "," + this.postfix;
		} else {
			return this.prefix;
		}
	}
	
}
