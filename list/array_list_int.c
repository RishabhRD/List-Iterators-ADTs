#include <stdlib.h>
#include <stdio.h>

typedef struct{
  int size;
  int capacity;
  int* ar;
}IntList;
IntList createIntList(){
  IntList list;
  list.size = 0;
  list.capacity = 13;
  int* ar = (int*) malloc(list.capacity*sizeof(Node));
  list.ar = ar;
  return list;
}
Node get(IntList list,int index){
  int* ar = list.ar;
  return *(ar+index);
}
int getSize(IntList list){
  return list.size;
}
void add(IntList *list,int ele){
  if((*list).size==(*list).capacity){
    int* new = (int*) malloc(list->capacity*2*sizeof(Node));
    list->capacity = list->capacity*2;
    int* ar = list->ar;
    for(int i=0;i<list->size;i++){
      new[i] = ar[i];
    }
    list->ar = new;
  }
  int* ar = list->ar;
  ar[list->size] = ele;
  list->size = list->size+1;

}
