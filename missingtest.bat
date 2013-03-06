echo eSalesCommonClient
java -cp bin lmig.util.LmigUtil missingtests "D:\Workspaces\PLM\PmESalesCommonClient\src\main\webapp" "D:\Workspaces\PLM\PmESalesCommonClient\src\main\webapp\test\unit" "js" test\,.profile

echo eSalesDesktopCommonClient
java -cp bin lmig.util.LmigUtil missingtests "D:\Workspaces\PLM\PmESalesDesktopCommonClient\src\main\webapp" "D:\Workspaces\PLM\PmESalesDesktopCommonClient\src\main\webapp\test\unit" "js" test\,.profile

echo eSalesDesktopAutoClient
java -cp bin lmig.util.LmigUtil missingtests "D:\Workspaces\PLM\PmESalesDesktopAutoClient\src\main\webapp" "D:\Workspaces\PLM\PmESalesDesktopAutoClient\src\main\webapp\test\unit" "js" test\,.profile