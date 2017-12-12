/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so2;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sergio Barros
 */
public class Ordenacao{
    int i =0,j=0,aux2,braco,max, v,s,aux,dir,mais=braco,menos=braco,vix,menor,controle;
    double mfcfs,mssf,mscan,mcscan;
    String ffcfs="dados FCFS\n",sssf="dados SSF\n",sscan="dados SCAN\n",ccscan="dados C-SCAN\n";
    ArrayList fcfs = new ArrayList();
    ArrayList ssf = new ArrayList();
    ArrayList scan = new ArrayList();
    ArrayList cscan = new ArrayList();
    ArrayList auxiliar = new ArrayList();
    ArrayList auxiliar2 = new ArrayList();
    String[] x=null;
    
    public void arquivo(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o Numero de Cilindros maior que "+this.mais+":");
        max = Integer.parseInt(sc.nextLine());
        
    }
    
    public Ordenacao(String[] x){
        this.menos=Integer.parseInt( x[0]);
        for(i=0;i<x.length;i++){
            v = Integer.parseInt( x[i]);
            
            this.fcfs.add(v);
            this.scan.add(v);
            if(this.mais<v)
                this.mais=v;
            if(this.menos>v)
                this.menos=v;
                
            }
        this.i=0;
    }
    public boolean max(int max){
        if(max<mais)
            return false;
        this.max=max;
        return true
;    }
    public void braco(int braco){
        this.ssf(braco);
        this.scan(braco);
        this.cscan(braco);
        fcfs.add(0, braco);
        ssf.add(0,braco);
        scan.add(0,braco);
        cscan.add(0,braco);
    }
    public void ssf(int braco){
        int v,aux;
        aux = fcfs.size();
        controle = braco;
        for(i=0;i<aux;i++){
            
            aux2=scan.size();
            menor=(int) scan.get(0);
            for(j=0;j<aux2;j++){
                v = (int) scan.get(j);             
                vix=Math.abs(v-controle);
                if(Math.abs(menor-controle)>vix){
                    menor=v;
                }
            }
            controle=menor;
            ssf.add(i,menor);
            auxiliar.add(i,menor);
            auxiliar2.add(i,menor);
            scan.remove(scan.indexOf(menor));
        }
    }
    public void scan(int braco){
        int i=0,v,s;
        aux = fcfs.size();
        v=this.max/2;
        if(v-braco>0){
            dir = -1;}
        else
            dir=1;
        
        while(scan.size()!=aux){
              if(dir>0)
                for(i=braco;i<=mais;i++){
                    if(auxiliar.contains(i)){
                    scan.add(i);
                    auxiliar.remove(auxiliar.indexOf(i));
                    i-=dir;
                    }
                    
            }
            if (dir<0)
                 for(i=braco;i>=menos;i--){
                    if(auxiliar.contains(i)){
                        scan.add(i);
                        auxiliar.remove(auxiliar.indexOf(i));
                        i-=dir;
                    }
                    }
            dir=-dir;
        }
        
    }
    public void cscan(int braco){
        int v,s,i=0;
        aux = fcfs.size();
        v=this.max/2;
        if(v>braco)
            dir = -1;
        else
            dir=1;
    
        while(cscan.size()!=aux){
            
            if(dir>0){
                for(i=braco;i<=mais;i++){
                    if(auxiliar2.contains(i)){
                    cscan.add(i);
                    auxiliar2.remove(auxiliar2.indexOf(i));
                    i-=dir;
                   
                    }                
                }
                for(i=menos;i<=braco;i++){
                    if(auxiliar2.contains(i)){
                        cscan.add(i);
                        auxiliar2.remove(auxiliar2.indexOf(i));
                        i-=dir;
                    }
                }
            }
            if (dir<0){
                for(i=braco;i>=menos;i--){
                    if(auxiliar2.contains(i)){
                        cscan.add(i);
                        auxiliar2.remove(auxiliar2.indexOf(i));
                        i-=dir;
                    }
                }
                for(i=mais;i>=braco;i--){
                    if(auxiliar2.contains(i)){
                        cscan.add(i);
                        auxiliar2.remove(auxiliar2.indexOf(i));
                        i-=dir;
                    }
                 }
            }
        }
    }
    public int movimentos(ArrayList lista){
        int b,t=0;
        
        for(i=1;i<lista.size();i++){
            b=(int) lista.get(i-1);
            b-=(int)lista.get(i);
            t+=Math.abs(b);
        }
        return t;
    }
     public int movimentoscan(){
        int b,t=0;
        if((this.max/2)>braco){
            t=menos*2;
        }
        else{
            t=(this.max-mais)*2;
        }
        
        for(i=1;i<scan.size();i++){
            b=(int) scan.get(i-1);
            b-=(int)scan.get(i);
            t+=Math.abs(b);
            System.out.println(Math.abs(b));
        }
        
        return t;
    }
    
    
    
    public double variancia(ArrayList lista,double med){
        double b,a,t=0;

        for(i=1;i<lista.size();i++){
            b=(int) lista.get(i-1);
            a=(int)lista.get(i);
            
            t+=Math.pow((Math.abs(b-a)-med),2);
            System.out.println("t= "+t);
        }
        return t/(lista.size()-2);
    }
    public double varianciascan(){
        double v,b,a,t=0;
        
        for(i=1;i<scan.size();i++){
            b=(int) scan.get(i-1);
            a=(int)scan.get(i);
            /*if(b==menos && a!=menos)
                t+=Math.pow((Math.abs(b-a)+menos*2-mscan),2);
            if(b==mais && a!=mais)
                t+=Math.pow((Math.abs(b-a)+(max-mais)*2-mscan),2);
            else*/
                t+=Math.pow((Math.abs(b-a)-mscan),2);
            System.out.println("t= "+t);
        }
        return t/(scan.size()-2);
    }
    
     public double varianciacscan(){
         double b,a,t=0;
         for(i=1;i<cscan.size();i++){
            b=(int) cscan.get(i-1);
            a=(int)cscan.get(i);
            
           // if (b==menos && a!=menos){
            //    t+=Math.pow((b+this.max-a)+this.max-mcscan, 2);
           // }
          //  else if((b==mais && a!=mais)){
                //t+=Math.pow((this.max-b+a)+this.max-mcscan, 2);
        //    }
         //   else{
                t+=Math.pow(Math.abs(b-a)-mcscan, 2);
       //     }
        }
        return t/(cscan.size()-2);
     }
    
    public int movimentocscan(){
        int b,a,t=0;
        for(i=1;i<cscan.size();i++){
            b=(int) cscan.get(i-1);
            a=(int)cscan.get(i);
            if (dir==-1 && b==menos && a!=menos)
                t+=(b+this.max-a)+this.max;
            else if((dir==1 && b==mais && a!=mais))
                t+=(this.max-b+a)+this.max;
            else
                t+=Math.abs(b-a);
        }
        return t;
    }
    public void mediafcfs(){
        double v=movimentos(this.fcfs);
        this.mfcfs=v/(this.fcfs.size()-1);
        this.ffcfs+=("quantidade de movimentos "+v+"\n"+"média de movimentos: "+this.mfcfs+"\n");
        
    }
    public void mediassf(){
        double v=movimentos(this.ssf);
        this.mssf=v/(this.ssf.size()-1);
        this.sssf+=("quantidade de movimentos "+v+"\n"+"média de movimentos: "+this.mssf+"\n");
    }
    public void mediascan(){
        double v=movimentoscan();
        this.mscan=v/(this.scan.size()-1);
        this.sscan+=("quantidade de movimentos "+v+"\n"+"média de movimentos: "+this.mscan+"\n");
    }
    public void mediacscan(){
        double v=movimentocscan();
        this.mcscan=v/(this.cscan.size()-1);
        this.ccscan+=("quantidade de movimentos "+v+"\n"+"média de movimentos: "+this.mcscan+"\n");
    }
   // public int variancia(ArrayList lista,boolean c){
        
   // }
    
    
    
}
