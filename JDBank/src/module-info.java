/**
 * 
 */
/**
 * 
 */
module JDBank {
	requires java.base;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.annotation;
	requires java.xml;
	opens com.JDBank to com.fasterxml.jackson.databind;
}