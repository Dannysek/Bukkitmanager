package org.efreak.bukkitmanager.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

	String label();
	boolean alias() default true;
	String helpNode() default "";
	String permission() default "";
	String usage();
	boolean hideHelp() default false;

}