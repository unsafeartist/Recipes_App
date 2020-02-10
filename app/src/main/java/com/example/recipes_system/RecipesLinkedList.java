package com.example.recipes_system;/*
@author Kamaljot Saini

THIS IS NOT IMPLEMENTED IN THE PROJECT!
 */

public class RecipesLinkedList<itemType> {
    //Referencing old implementation of my linkedList
    //in C++, trying to figure out how to implement in JAVA

    private RecipesNode<itemType> ptr_head;
    private int sizeCounter;

    public RecipesLinkedList() {
    }

    public RecipesLinkedList(RecipesNode<itemType> ptr_head, int sizeCounter) {
        this.ptr_head = ptr_head;
        this.sizeCounter = sizeCounter;
    }

    public RecipesNode<itemType> getPtr_head() {
        return ptr_head;
    }

    public void setPtr_head(RecipesNode<itemType> ptr_head) {
        this.ptr_head = ptr_head;
    }

    public int getSizeCounter() {
        return sizeCounter;
    }

    public void setSizeCounter(int sizeCounter) {
        this.sizeCounter = sizeCounter;
    }

    public void addFirst(itemType input_data)
    {
        //Following a similar naming convention for variables
        //as my linkedList in C++ for easier understanding
        RecipesNode<itemType> temporaryPtr;
        RecipesNode<itemType> newRecipesNode;

        //Create the new node to be added to linked list
        newRecipesNode = new RecipesNode<itemType>();

        //Insert the data into the new node
        newRecipesNode.setItem_data(input_data);

        /*
        If the linked list is full add appropriately, otherwise
        add to the beginning
         */
        if(ptr_head != null)
        {
            temporaryPtr = ptr_head;
            ptr_head = newRecipesNode;
            newRecipesNode.setNext(temporaryPtr);
            sizeCounter++;
        }
        else
        {
            ptr_head = newRecipesNode;
            sizeCounter++;
        }
    }

    public void addLast(itemType input_data)
    {
        RecipesNode<itemType> temporary_ptrHolder;
        RecipesNode<itemType> newRecipesNode;
        newRecipesNode = new RecipesNode<itemType>();
        newRecipesNode.setItem_data(input_data);
        temporary_ptrHolder = ptr_head;

        if(temporary_ptrHolder == null)
        {
            ptr_head = newRecipesNode;
            sizeCounter++;
        }
        else
        {
            while(temporary_ptrHolder.getNext() != null)
            {
                temporary_ptrHolder = temporary_ptrHolder.getNext();
            }

            temporary_ptrHolder.setNext(newRecipesNode);
        }

    }

    public void deleteFirst()
    {
        RecipesNode<itemType> temp = ptr_head;
        ptr_head = ptr_head.getNext();
        sizeCounter++;
    }

    public void deleteLast()
    {
        RecipesNode<itemType> curr;
        RecipesNode<itemType> next;

        curr = ptr_head;
        next = ptr_head.getNext();

        while(next.getNext() != null)
        {
            next = next.getNext();
            curr = curr.getNext();
        }

        curr.setNext(null);

        sizeCounter--;
    }

    public int search(itemType search_item)
    {
        int counter_timesFound = 0;

        RecipesNode<itemType> temporary_ptrHolder;

        temporary_ptrHolder = ptr_head;

        while(temporary_ptrHolder.getNext() != null)
        {
            if(temporary_ptrHolder.getItem_data() == search_item)
                counter_timesFound++;

            temporary_ptrHolder = temporary_ptrHolder.getNext();
        }

        return counter_timesFound;
    }

    public void emptyList()
    {
        RecipesNode<itemType> temp_ptr;
        RecipesNode<itemType> deletion_Ptr = ptr_head;

        while(deletion_Ptr.getNext() != null)
        {
            temp_ptr = deletion_Ptr.getNext();
            sizeCounter--;
            deletion_Ptr = temp_ptr;
        }

        sizeCounter--;
        ptr_head = null;
    }

    public void displayList()
    {
        RecipesNode<itemType> recipesNodePtr = ptr_head;

        while(recipesNodePtr != null)
        {
            System.out.println(recipesNodePtr.getItem_data());
            recipesNodePtr = recipesNodePtr.getNext();
        }
    }

}
