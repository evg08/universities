package org.levelup.thread.flowerbed;

public class App {
    public static void main(String[] args) {
        Flower queue=new FlowerBed(40);
        new Thread(new Flowers(queue),"Flowers").start();
        //2 garden men
        String name1="GardenMan -1";
        String name2="GardenMan -2";
        GardenMan  p1=new GardenMan (name1,queue);
        GardenMan  p2=new GardenMan (name2,queue);
        new Thread(p1,name1).start();
        new Thread(p2,name2).start();




    }
}
