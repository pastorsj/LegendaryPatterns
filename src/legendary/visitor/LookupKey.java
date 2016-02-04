package legendary.visitor;

/**
 * This class creates a hash key between the VisitType and the class
 */
class LookupKey {
	
	/** The visit type. */
	VisitType visitType;
	
	/** The class. */
	Class<?> clazz;
	
	/**
	 * Instantiates a new lookup key.
	 *
	 * @param visitType the visit type
	 * @param clazz the clazz
	 */
	public LookupKey(VisitType visitType, Class<?> clazz) {
		this.visitType = visitType;
		this.clazz = clazz;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((visitType == null) ? 0 : visitType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LookupKey other = (LookupKey) obj;
		if (visitType != other.visitType)
			return false;
		
		if(!other.clazz.isAssignableFrom(this.clazz))
			return false;
		
		return true;
	}
	
	
}
