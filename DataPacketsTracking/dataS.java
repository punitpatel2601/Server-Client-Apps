import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class sorts the data packets which were tracked while downloading files
 * on a virtual machine on Google Cloud Platform, the data is then used to
 * compute average data packet size, throughput and to track the ports having
 * the most traffic.
 * 
 * The result is saved in report.pdf
 * 
 * The traced packet are in OutFile.txt
 */
public class dataSort {
    public static void main(String[] args) {
        String input = null;
        String[] data = new String[9];
        for (int a = 0; a < 9; a++)
            data[a] = " ";
        int[] seqNo = new int[73048];
        int[] size = new int[73048];
        double[] time = new double[73048];
        String[] ip_src = new String[73048];
        String[] ip_dst = new String[73048];
        String[] udp_src_port = new String[73048];
        String[] udp_dst_port = new String[73048];

        String[] tcp_src_port = new String[73048];
        String[] tcp_dst_port = new String[73048];
        int i = 0;

        try {
            input = null;
            File myfile = new File("OutFile.txt");
            Scanner sc = new Scanner(myfile);

            while (sc.hasNextLine()) {
                input = sc.nextLine();
                String[] temp = input.split(",");
                if (i == 73756 && temp[6].isEmpty())
                    System.out.println("helo" + temp[6] + "yp");

                for (int a = 0; a < temp.length; a++) {
                    data[a] = temp[a];
                }

                try {
                    seqNo[i] = Integer.parseInt(data[0]);
                    size[i] = Integer.parseInt(data[1]);
                    time[i] = Double.parseDouble(data[2]);
                    ip_src[i] = data[3];
                    ip_dst[i] = data[4];
                    udp_src_port[i] = data[5];
                    udp_dst_port[i] = data[6];
                    tcp_src_port[i] = data[7];
                    tcp_dst_port[i] = data[8];
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(data[0]);
                }
                i++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        long avg_size = 0;
        for (int a = 0; a <= 73047; a++)
            avg_size += size[a];
        avg_size = avg_size / 73048;
        System.out.println("Average size:" + avg_size);

        long traffic = 0;
        for (int a = 0; a < 73048; a++)
            if (ip_dst[a].equals("10.128.0.2"))
                traffic += size[a];
        System.out.println(ip_src[73046]);
        double throughput = traffic / (time[73047] - time[0]);
        System.out.println("Throughput:" + throughput);

        String[] tcpSrcPorts = new String[14];
        int count = 0;

        for (int p = 0; p < 73048; p++) {
            int j;
            for (j = 0; j < p; j++)
                if (tcp_src_port[p].equals(tcp_src_port[j]))
                    break;

            if (p == j) {
                tcpSrcPorts[count] = tcp_src_port[p];
                count++;
            }
        }

        int flag = 0;
        for (int x = 0; x < 73048; x++) {
            flag = 0;
            for (int c = 0; c < count; c++)
                if (tcp_src_port[x].equals(tcpSrcPorts[c]))
                    flag = 1;
        }
        if (flag == 1)
            System.out.println("done");
        System.out.println("There are " + count + " tcp source ports");

        String[] tcpDstPorts = new String[20];
        int count2 = 0;

        for (int p = 0; p < 73048; p++) {
            int j;
            for (j = 0; j < p; j++)
                if (tcp_dst_port[p].equals(tcp_dst_port[j]))
                    break;

            if (p == j) {
                tcpDstPorts[count2] = tcp_dst_port[p];
                count2++;
            }
        }

        System.out.println("There are " + count2 + " tcp destination ports");

        long[] srcTraffic = new long[14];
        for (int l = 0; l < 14; l++)
            srcTraffic[l] = 0;
        int choice = 0;

        for (int a = 0; a < 73048; a++) {
            for (int j = 0; j < 14; j++)
                if (tcp_src_port[a].equals(tcpSrcPorts[j])) {
                    choice = j;
                    break;
                }
            srcTraffic[choice] += size[a];
        }

        long[] dstTraffic = new long[14];
        for (int l = 0; l < 14; l++)
            dstTraffic[l] = 0;
        choice = 0;

        for (int a = 0; a < 73048; a++) {
            for (int j = 0; j < 14; j++)
                if (tcp_dst_port[a].equals(tcpDstPorts[j])) {
                    choice = j;
                    break;
                }
            dstTraffic[choice] += size[a];
        }

        long totalTraffic = 0;
        for (int a = 0; a < 73048; a++)
            totalTraffic += size[a];

        for (int f = 0; f < 14; f++) {
            for (int g = 0; g < 14; g++) {
                if (srcTraffic[f] < srcTraffic[g]) {
                    long temp = srcTraffic[f];
                    String temp2 = tcpSrcPorts[f];

                    srcTraffic[f] = srcTraffic[g];
                    tcpSrcPorts[f] = tcpSrcPorts[g];

                    srcTraffic[g] = temp;
                    tcpSrcPorts[g] = temp2;

                }

                if (dstTraffic[f] < dstTraffic[g]) {
                    long temp = dstTraffic[f];
                    String temp2 = tcpDstPorts[f];

                    dstTraffic[f] = dstTraffic[g];
                    tcpDstPorts[f] = tcpDstPorts[g];

                    dstTraffic[g] = temp;
                    tcpDstPorts[g] = temp2;
                }
            }
        }

        long totalSrcTraffic = 0;
        for (int x = 0; x < 14; x++) {
            System.out.println(tcpSrcPorts[x] + ":" + srcTraffic[x]);
            totalSrcTraffic += srcTraffic[x];
        }

        System.out.println("_____________DST_____________");
        long totalDstTraffic = 0;
        for (int x = 0; x < 14; x++) {
            System.out.println(tcpDstPorts[x] + ":" + dstTraffic[x]);
            totalDstTraffic += dstTraffic[x];
        }

        System.out.println("TOTAL SRC TRAFFIC is " + totalSrcTraffic);
        System.out.println("TOTAL DST TRAFFIC is " + totalDstTraffic);
        System.out.println("TOTAL SYSTEM TRAFFIC is " + totalTraffic);

        System.out.println(
                "top three receiver ports :" + tcpDstPorts[13] + ">" + tcpDstPorts[12] + ">" + tcpDstPorts[11]);
        double recContribution1 = ((double) dstTraffic[13] / totalDstTraffic) * 100;
        double recContribution2 = ((double) dstTraffic[12] / totalDstTraffic) * 100;
        double recContribution3 = ((double) dstTraffic[11] / totalDstTraffic) * 100;

        System.out.println("Their required percentsges are :" + recContribution1 + "% " + recContribution2 + "% "
                + recContribution3 + "% ");

        System.out
                .println("top three sender ports :" + tcpSrcPorts[13] + ">" + tcpSrcPorts[12] + ">" + tcpSrcPorts[11]);
        double senContribution1 = ((double) srcTraffic[13] / totalSrcTraffic) * 100;
        double senContribution2 = ((double) srcTraffic[12] / totalSrcTraffic) * 100;
        double senContribution3 = ((double) srcTraffic[11] / totalSrcTraffic) * 100;

        System.out.println("Their required percentsges are :" + senContribution1 + "% " + senContribution2 + "% "
                + senContribution3 + "% ");
    }
}
