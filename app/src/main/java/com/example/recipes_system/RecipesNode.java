package com.example.recipes_system;

/*
@author Kamaljot Saini
Attempting to create a node class for linkedList.

THIS IS NOT IMPLEMENTED IN THE PROJECT!

 */
public class RecipesNode<genericType> {
    private genericType item_data;
    private RecipesNode<genericType> next;

    public RecipesNode() {
    }

    public RecipesNode(genericType item_data, RecipesNode<genericType> next) {
        this.item_data = item_data;
        this.next = next;
    }

    public genericType getItem_data() {
        return item_data;
    }

    public void setItem_data(genericType item_data) {
        this.item_data = item_data;
    }

    public RecipesNode<genericType> getNext() {
        return next;
    }

    public void setNext(RecipesNode<genericType> next) {
        this.next = next;
    }
}
