package de.doridian.steammobile.connection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface MessageListener {
	@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) public @interface Handler { }
}
