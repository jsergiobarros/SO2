package so2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;

public class Principal {

	private static final String FILENAME = "C:\\Users\\Sergio Barros\\Desktop\\";
        
	public static void main(String[] args) throws InterruptedException  {
            Scanner sc = new Scanner(System.in);
            boolean cont=false;
            entradas ent = new entradas();
            ent.setVisible(true);
            BufferedReader br = null;
            FileReader fr = null;
            int i =0,max,braco=0;
            double tempo=0;
            String[] x=null;
            String nome;
            
            do{
            System.out.print("Digite o Nome do Arquivo:");
            //nome = sc.nextLine();
            ent.v.acquire();
            nome=ent.nome;
           
            try {
                
                //br = new BufferedReader(new FileReader(FILENAME));
                fr = new FileReader(FILENAME+nome+".txt");
                br = new BufferedReader(fr);
                
                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                        System.out.println(sCurrentLine);
                        x = sCurrentLine.split("-");
                        System.out.println(x.length);
                        
                    }
                cont=true;
                //ent.v.release();
            } catch (IOException e) {
                System.out.println("arquivo inexistente");
                ent.label1v();
                cont=false;
                //e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                            br.close();
                    if (fr != null)
                            fr.close();
                } catch (IOException ex) {
                    cont=false;
                
                    //ex.printStackTrace();
                }
        }
            }while(cont==false);
            
             cont=false;
            Ordenacao ord = new Ordenacao(x);
            ent.mais=ord.mais;
            System.out.println(ent.max);
            ent.entrada1();
            do{
                try{
                    System.out.print("Digite o Numero de Cilindros:");
                    ent.v.acquire();
                    //nome=;
                    max = Integer.parseInt(ent.nome);
                    ent.max=max;
                    cont=ord.max(max);
                }catch(Exception e){
                    System.out.println("Valor inválido, Tente novamente");
                    ent.label1v();
                    cont=false;
                    continue;}
            }while(cont==false);
            ent.entrada2();
            cont=false;
            do{
                try{
                    System.out.print("Digite onde está o braço:");
                    ent.v.acquire();
                    braco = Integer.parseInt(ent.nome);
                   
                    cont=true;
                }catch(Exception e){
                    cont=false;
                    System.out.println("Valor inválido, Tente novamente");
                    ent.label1v();
                    continue;}
                }while(braco>ord.max || cont==false);
            
            cont=false;
            ent.entrada3();
            do{
                try{
                    System.out.print("Digite o tempo de Seek em milisegundos:");
                    ent.v.acquire();
                    tempo = Double.parseDouble(ent.nome);
                    cont=true;
                }catch(Exception e){
                    System.out.println("Valor inválido, Tente novamente");
                    ent.label1v();
                    continue;
                }
            }while(cont==false);
            
            ord.braco(braco);
            
            
            ord.scan(braco); 
            System.out.println("xx"+i);
            for(i=0;i<x.length;i++){
                System.out.println(ord.ssf.get(i));
            }
            ord.cscan(braco);

            System.out.println("scan "+ord.scan.size());
            System.out.println("fcfs "+ord.fcfs.size());
            System.out.println("ssf "+ord.ssf.size());        
            for(i=0;i<x.length;i++){
                System.out.println(i+" fcfs "+ord.cscan.get(i));
            }
            try{
                FileWriter arq = new FileWriter("C:\\Users\\Sergio Barros\\Desktop\\"+nome+"FCFS.txt");
                PrintWriter gravarArq = new PrintWriter(arq);
                gravarArq.printf("Ordem de leitura do FCFS - ");
                for(i=0;i<ord.fcfs.size();i++){
                    gravarArq.printf(ord.fcfs.get(i)+"-");                    
                }
                arq.close();
            }
            catch(IOException e){}
            try{
                FileWriter arq = new FileWriter("C:\\Users\\Sergio Barros\\Desktop\\"+nome+"SSF.txt");
                PrintWriter gravarArq = new PrintWriter(arq);
                gravarArq.printf("Ordem de leitura do SSF - ");
                for(i=0;i<ord.ssf.size();i++){
                    gravarArq.printf(ord.ssf.get(i)+"-");
                }
                arq.close();
            }
            catch(IOException e){} 
            try{
                FileWriter arq = new FileWriter("C:\\Users\\Sergio Barros\\Desktop\\"+nome+"SCAN.txt");
                PrintWriter gravarArq = new PrintWriter(arq);
                gravarArq.printf("Ordem de leitura do SCAN - ");
                for(i=0;i<ord.scan.size();i++){
                    gravarArq.printf(ord.scan.get(i)+"-");  
                }
                //gravarArq.printf("   "+ord.tempo(tempo, ord.scan));
                arq.close();
            }
            
            catch(IOException e){} 
            try{
                FileWriter arq = new FileWriter("C:\\Users\\Sergio Barros\\Desktop\\"+nome+"CSCAN.txt");
                PrintWriter gravarArq = new PrintWriter(arq);
               // gravarArq.printf("Ordem de leitura do C-SCAN - ");
                for(i=0;i<ord.cscan.size();i++){
                    gravarArq.printf(ord.cscan.get(i)+"-");                    
                }
                
                arq.close();
            }
            catch(IOException e){}
            
          
            System.out.println(ord.fcfs.size()+" "+ord.ssf.size()+" "+ord.cscan.size()+" "+ord.scan.size()+" ");
            System.out.println(ord.movimentocscan()+" "+ord.movimentos(ord.fcfs)+" "+ord.movimentos(ord.ssf)+" "+ord.movimentos(ord.scan));
            ord.mediafcfs();
            ord.mediascan();
            ord.mediassf();
            ord.mediacscan();
            ord.ffcfs+="Tempo médio de deslocamento: "+ord.mfcfs*tempo+"milisegundos\n";
            ord.sssf+="Tempo médio de deslocamento: "+ord.mssf*tempo+"milisegundos\n";
            ord.sscan+="Tempo médio de deslocamento: "+ord.mscan*tempo+"milisegundos\n";
            ord.ccscan+="Tempo médio de deslocamento: "+ord.mcscan*tempo+"milisegundos\n";
            double auxx;
            auxx=ord.variancia(ord.fcfs, ord.mfcfs);
            ord.ffcfs+=("variancia "+auxx+"\n"+"desvio padrão "+Math.pow(auxx, 0.5)+"\n");
            auxx=ord.variancia(ord.ssf, ord.mssf);
            ord.sssf+=("variancia "+auxx+"\n"+"desvio padrão "+Math.pow(auxx, 0.5)+"\n");
            auxx=ord.variancia(ord.scan, ord.mscan);
            ord.sscan+=("variancia "+auxx+"\n"+"desvio padrão "+Math.pow(auxx, 0.5)+"\n");
            auxx=ord.variancia(ord.cscan, ord.mcscan);
            ord.ccscan+=("variancia "+auxx+"\n"+"desvio padrão "+Math.pow(auxx, 0.5)+"\n");
            System.out.println(ord.ffcfs);
            System.out.println(ord.sssf);
            System.out.println(ord.sscan);
            System.out.println(ord.ccscan);
            ent.fim(ord.ffcfs,ord.sssf,ord.sscan,ord.ccscan);
        }

}