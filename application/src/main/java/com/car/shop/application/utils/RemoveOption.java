package com.car.shop.application.utils;

public enum RemoveOption {
	
	WHOLE {
		@Override
		public <V> V accept(RemoveOptionVisitor<V> visitor) {
			return visitor.visitWhole();
		}
	}, 
	
	SINGLE {
		@Override
		public <V> V accept(RemoveOptionVisitor<V> visitor) {
			return visitor.visitSingle();
		}
	};
	
	public interface RemoveOptionVisitor<V> {
		
		V visitWhole();
		
		V visitSingle();
		
	}
	
	public abstract <V> V accept(RemoveOptionVisitor<V> visitor);

}
