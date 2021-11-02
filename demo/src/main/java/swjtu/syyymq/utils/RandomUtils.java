package swjtu.syyymq.utils;

public class RandomUtils {
    /**
     * @param low 低值参数
     * @param high 高值参数
     * @return 获取高值低值之间的一个随机数
     * @throws Exception 低值不小于高值
     */
    public static int getRandom(int low,int high) throws Exception {
        if(low>=high){
            // TODO：自定义异常
            throw new Exception("输入参数格式错误！！！");
        }
        return (int)(Math.random()*(high-low))+low;
    }

    /**
     * @param length 随机数的位数
     * @return 随机数
     * @throws Exception 低值不小于高值
     */
    public static int getRandom(int length) throws Exception {
        int m=getNumber(length);
        int n=m*10-1;
        return getRandom(m, n);
    }

    /**
     *
     * @param n 返回数字的位数
     * @return n位数的最低值
     */
    private static int getNumber(int n){
        if(n<1){
            n=1;
        }
        if(n==1){
            return 1;
        }else{
            n=n-1;
            return 10*getNumber(n);
        }
    }
}
