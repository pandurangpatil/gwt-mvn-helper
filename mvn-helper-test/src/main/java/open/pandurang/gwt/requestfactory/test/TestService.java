package open.pandurang.gwt.requestfactory.test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import open.pandurang.gwt.helper.requestfactory.marker.RFService;
import open.pandurang.gwt.helper.requestfactory.marker.RFServiceMethod;


@RFService(value = TestLocator.class)
public class TestService {

	@RFServiceMethod
	public String getUserName(Set<Integer> id) {
		return null;
	}

	@RFServiceMethod
	public Value tryEntityValue(Set<Entity> id) {
		return null;
	}

	@RFServiceMethod
	public List<List<List<Value>>> trySomethingComplicatedfsadfa(List<List<List<Entity>>> in) {
		return null;
	}

	@RFServiceMethod
	public Set<List<Map<String, Value>>> trySomethingComplicated(Value id, List<String> sec) {
		return null;
	}

	/*
	 * This is a negative test case to test it uncomment the below method to check if plugin throws error
	 */

	// @RFServiceMethod
	// public Set<List<Map<String, File>>> negativeUnsupportedType(File id, List<String> sec) {
	// return null;
	// }

	@RFServiceMethod
	public String arrayParams(Entity[][] params) {
		return null;
	}

	@RFServiceMethod
	public String arrayParams(String[][] params) {
		return null;
	}

	@RFServiceMethod
	public String[] arrayReturn(String[][] params) {
		return null;
	}

	@RFServiceMethod
	public String genericArrayParam(Set<Double[]> id) {
		return null;
	}

	@RFServiceMethod
	public int getSampleInteger() {
		return 0;
	}

	/*
	 * TODO: Need to take of situation of return type in below method
	 */
	@RFServiceMethod
	public List<List<List<Value>[][]>[][]> genericArrayParamproxy(Set<Entity[]> id) {
		return null;
	}
}
