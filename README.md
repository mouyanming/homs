# HOMSとは?
	Hyron Operation Management System の略称となります。

### 1.ローカル開発環境の構築

#### 概要
本項目では、ハイロン内部で開発環境を構築する方法を記載します。

#### 必要な知識
1. Git
2. SpringBoot(Spring MVC,Spring Security,Spring data JPA)
3. Thymeleaf
4. Eclipse
5. maven
6. html5+css3+javascript+jQuery+bootstrap

#### 開発環境セットアップ
**必須インストールソフト**  
 下記ソフトウエアをローカルPCにインストールしてください。
> **Java SE Development Kit 8**  
>  http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
>
>  JDKをインストールしたら、環境変数の設定が必要だ。環境変数とはOSが使用する共通の変数のことで、
>  環境変数にJDKに関する設定を行うことでJavaが正しく動作する。
>  設定する環境変数は「Path」と「JAVA_HOME」の2種類だ。（設定方法省略）

> **Maven 3**  
>   https://maven.apache.org/download.cgi  
>   環境変数は「Path」と「MVN_HOME」の2種類だ。（設定方法省略）

> **Git**  
>   https://git-for-windows.github.io/

> **Eclipse (Luna以上)**  
>   https://www.eclipse.org/downloads/

>   ***plugin***  
>   Eclipse->Help->Eclipse Maketplace  
>   1.Spring Tool Suite  
>   2.lombok (https://projectlombok.org/download)

**インストールしたほうが良いソフト**  
> **SourceTree**  
>   https://ja.atlassian.com/software/sourcetree


#### プロジェクト導入
1. ソースダウンロード  
`git clone git@github.com:gyomei/homs.git`
2. Eclipse起動
3. File->Import->Maven->Existing Maven Projects->Next
4. 1でダウンロードしたファイルパスをRoot Directoryに選択
5. pom.xmlを選択してFinishを押す

#### プロジェクト起動
1. Eclipse
    1. homsを右クリックして
    2. Run As->Spring Boot App
2. コマンドライン
    1. CMD起動
    2. cd ソースファイルパス
    3. 下記コマンド実行  
     `mvn spring-boot:run`


