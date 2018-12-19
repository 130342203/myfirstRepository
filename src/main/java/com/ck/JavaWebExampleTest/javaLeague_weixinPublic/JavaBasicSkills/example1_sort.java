package com.ck.JavaWebExampleTest.javaLeague_weixinPublic.JavaBasicSkills;
/*java联盟之排序*/
public class example1_sort {

    public static void main(String[] args) {
        testMain8();
    }

    private static void testMain1(){
        /*案例一、冒泡排序
         * 原理：丛首位开始，与相邻记录对比，得出更大或者更小的记录交换后再与下一位交换，直至一轮交换完毕（得出该轮最大或者最小的数）
         * 进入下一轮从第二位开始进行一轮比较，以此类推直至最后一位。。
         * 优化：详见案例二
         * */
        int [] a = {4,2,5,5,2,6,7,8,9,6,0};
        bubbleSort(a);
        Print(a);
    }
    private static void testMain2(){
        /*案例二、冒泡排序-优化方案
        * 原理：根据案例一我们发现每一轮的比较中，即使遇到符合要求顺序的队列依然进行了后续轮次的比较
        * 这样会影响效率，浪费时间
        * 优化：案例二的优化只能对完全顺序的队列产生效果，更多优化详见案例三
        * */
        int [] a = {4,2,5,5,2,6,7,8,9,6,0};
        bubbleSort3(a);
        Print(a);
    }
    private static void testMain3(){
        /*案例三、冒泡排序-优化方案
         * 原理：在每一轮的所有交换中记录下来最后一次交换时j的坐标，在下一轮比较时只需要进行j次比较（因为
         * j坐标之后的队列已经是有序的队列）
         * 续章：案例四---选择排序
         * */
        int [] a = {4,2,5,5,2,6,7,8,9,6,0};
        bubbleSort2(a);
        Print(a);
    }
    private static void testMain4(){
        /*案例四、选择排序
         * 原理：1）首先通过n-1次比较，从n个数中找出最小的， 将它与第一个数交换——第一趟选择排序，结果最小的数被安置在第一个元素位置上。
2）再通过n-2次比较，从剩余的n-1个数中找出关键字次小的记录，将它与第二个数交换——第二趟选择排序。
3)重复上述过程，共经过n-1趟排序后，排序结束。
         * 续章：案例五-直接插入排序
         * */
        int [] a = {4,2,5,5,2,6,7,8,9,6,0};
        selectSort(a);
        Print(a);
    }
    private static void testMain5(){
/*案例五、直接插入排序
* 原理：1）数据存放在数组R[0….n-1]中，排序过程的某一中间时刻，R被划分成两个子区间R[0…i-1]和R[i….n-1]，
* 其中：前一个子区间是已排好序的有序区；后一个子区间则是当前未排序的部分。
2）将当前无序区的第1个记录R[i]插入到有序区R[0….i-1]中适当的位置，使R[0…i]变为新的有序区
3）当插入第i(i≥1)个对象时, 前面的r[0], r[1], …, r[i-1]已经排好序。
续章：优化：案例六-二分插入排序
* */
        int [] a = {4,2,5,5,2,6,7,8,9,6,0};
        insertSort(a);
        Print(a);
    }
    private static void testMain6(){
/*案例六、二分插入排序（直接插入排序优化）
 * 原理：在直接插入排序的基础上，利用二分(折半)查找算法决策出当前元素所要插入的位置。

二分查找：
找到中间元素，如果中间元素比当前元素大，则当前元素要插入到中间元素的左侧；否则，中间元素比当前元素小，
则当前元素要插入到中间元素的右侧。找到当前元素的插入位置 i 之后，把 i 和 high 之间的元素从后往前依次后移一个位置，
然后再把当前元素放入位置 i。
续章：案例七-希尔排序
         * */
        int [] a = {4,2,5,5,2,6,7,8,9,6,0};
        insertSort2(a);
        Print(a);
    }
    private static void testMain7(){
/*案例七、希尔排序
 * 原理：1）先取定一个小于 n 的整数 gap1 作为第一个增量，把整个序列分成 gap1 组。所有距离为 gap1 的倍数的元素放在同一组中，
 * 在各组内分别进行排序（分组内采用直接插入排序或其它基本方式的排序）。
2）然后取第二个增量gap2<gap1，重复上述的分组和排序。
3）依此类推，直至增量gap＝1，即所有元素放在同一组中进行排序为止。
续章：案例八-归并排序
         * */
        int [] a = {4,2,5,5,2,6,7,8,9,6,0};
        shellSort(a);
        Print(a);
    }
    private static void testMain8(){
/*案例八、归并排序
 * 原理：1）假设初始序列含有 n 个记录，则可看成 n 个有序的子序列，每个子序列长度为1。
2）两两合并，得到 n/2 个长度为2或1的有序子序列。
3）再两两合并，……如此重复，直至得到一个长度为n的有序序列为止。
续章：案例八-快速排序
         * */
        int [] a = {4,2,5,5,2,6,7,8,9,6,0};
        mergeSort(a,0,a.length-1);
        Print(a);
    }
    //归并排序
    private static void mergeSort(int[] a, int left, int right) {
        if (left<right) {
            //1）先分解
            //把整个数组分解成[left,mid][mid+1,right]——mid=(left+right)/2
            int mid = (left+right)/2;
            mergeSort(a, left, mid);
            mergeSort(a, mid+1, right);

            //2)在归并
            int[] b = new int[a.length];
            merge(a,b, left,mid, right);
            //把辅助序列b中的数据拷贝到a中
            copy(a,b,left,right);
        }
    }
    private static void merge(int[] a, int[] b, int left,int mid, int right) {
        int p = left;
        int r = mid+1;
        int k = left;
        while (p<=mid&&r<=right) {
            if (a[p]<a[r]) {
                b[k]=a[p++];
            }else {
                b[k]=a[r++];
            }
            k++;
        }
        if (p>mid) {
            for(int i=r;i<=right;i++){
                b[k++]=a[i];
            }
        }else{
            for(int i=p;i<=mid;i++){
                b[k++]=a[i];
            }
        }
    }
    private static void copy(int[] a, int[] b, int left, int right) {
        for(int i=left;i<=right;i++){
            a[i]=b[i];
        }
    }
    //希尔排序
    private static void shellSort(int[] a) {
        //进行分组，初始的gap=n/2，然后递减知道n=1；
        for(int gap=(a.length+1)/2;gap>0;){
            //分组冒泡
            for(int i=0;i<a.length-gap;i++){
                //组内排序
                for(int j=i;j<a.length-gap;j+=gap){
                    if (a[j]>a[j+gap]) {
                        swap(a, j, j+gap);
                    }
                }
            }
            if (gap>1) {
                gap=(gap+1)/2;
            }else if (gap==1) {
                break;
            }
        }
    }
    private static void insertSort2(int[] a) {
        for(int i=0;i<a.length-1;i++){
            int temp = a[i+1];//待插入的记录

            int low = 0;
            int high = i;
            int mid;

            while(low<=high) {//在已有的队列中找到中心点所处的位置
                mid = (low+high)/2;
                if (a[mid]>temp) {
                    high = mid-1;//已有队列的中心记录大于带插入记录，则降低高坐标
                }else{
                    low = mid+1;//反之，升高低坐标；//最终，直至低坐标大于高坐标结束循环
                }
            }
            for(int j=i;j>=high+1;j--){//将中心点加2往后的记录分别向后移动一位
                a[j+1]=a[j];
            }
            a[high+1]=temp;//最后将中心点加1的位置放入待插入点
        }
    }
    private static void insertSort(int[] a) {
        for(int i=0;i<a.length-1;i++){
            int temp = a[i+1];
            int j=i;
            while(a[j]>temp) {//每一轮都进行判断，若新插入的数（temp）小于比较的记录，则将比较的记录向后移动
                a[j+1]=a[j];
                j--;
                if (j<0) {
                    break;
                }
            }
            a[j+1]=temp;//当不满足前面的条件判断时，则将新插入的数插入最后的记录坐标处
        }
    }
    private static void selectSort(int[] a ){
        for (int i=0;i<a.length-1;i++){
            int k = i;
            for (int j=i+1;j<a.length;j++){
                if (a[j]<a[k]){
                    k = j;
                }
            }
            if (k!=i){
                swap(a,i,k);//本轮最小坐标k与i进行交换
            }
        }
    }
    private static void bubbleSort3(int[] a){
        int k = a.length-1;
        for (int i=0;i<a.length-1;i++){
            int n = 0;
            boolean flag = true;
            for (int j=0;j<k;j++){
                if (a[j]>a[j+1]){
                    swap(a,j,j+1);
                    flag = false;
                    n = j;//保存最后一次交换的下标
                }
            }
            if (flag){
                return;//案例二的优化处理
            }
            k = n;//最后一次交换的下标，减少后续比较的次数
        }
    }
    private static void bubbleSort2(int[] a){
        for (int i=0;i<a.length-1;i++){
            boolean flag = true;
            for (int j=0;j<a.length-i-1;j++){
                if (a[j]>a[j+1]){
                    swap(a,j,j+1);
                    flag = false;
                }
            }
            if (flag){
                break;
            }
        }
    }
    private static void bubbleSort(int[] a){
        for(int i=0;i<a.length-1;i++){
            for (int j=0;j<a.length-i-1;j++){
                if(a[j]>a[j+1]){//这里的交换决定了最后得出的队列是从小到大排序的
                    swap(a,j,j+1);
                }
            }
        }
    }
    private static void swap(int[]a,int i,int j ){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    private static void Print(int[] a){
        for (int num:a) {
            System.out.print(num+"\t");
        }
        System.out.println();
    }
}
