// treasuryInterface.aidl
package com.proj3cs478sravya.common;



interface treasuryInterface {

//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
boolean createDatabase();
List<DailyCash> dailyCash(int day, int month, int year, int range);
}
