package model;

import java.util.ArrayList;
import java.util.List;


public class Node {

	private boolean simple;
	private String token;
	private Object value;
	private List<Node> innerNodes;

	public static Node of(final boolean simple) {
		return new Node(simple);
	}
	
	public Node token(String token) {
		this.token = token;
		return this;
	}
	
	public Node value(Object value) {
		this.value = value;
		return this;
	}
	
	public Node innerNode(Node innerNode) {
		this.addNode(innerNode);
		return this;
	}
	
	public Node innerNode(List<Node> innerNodes) {
		this.innerNodes = innerNodes;
		return this;
	}
	
	private Node(final boolean simple) {
		super();
		this.simple = simple;
		innerNodes = new ArrayList<>();
	}
	
	public void addNode(final Node node) {
		if (innerNodes == null) {
			innerNodes = new ArrayList<>();
		}
		innerNodes.add(node);
	}
	
	public void removeNode(final Node node) {
		if (innerNodes != null && innerNodes.contains(node)) {
			innerNodes.remove(node);
		}
	}
	
	public boolean isSimple() {
		return simple;
	}
	public void setSimple(boolean simple) {
		this.simple = simple;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public List<Node> getInnerNodes() {
		return innerNodes;
	}
	public void setInnerNodes(List<Node> innerNodes) {
		this.innerNodes = innerNodes;
	}
}
