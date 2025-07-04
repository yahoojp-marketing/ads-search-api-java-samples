## バージョン

v17

## 概要

このサンプルプログラムは、Javaを使用して各APIを呼び出す処理のサンプルです。

## 内容物

src/main/
  - resources/
    - api_config.properties.dist    : 各種IDを記述する設定ファイルです。api_config.propertiesにリネームしてください。
  - java/jp/co/yahoo/adssearchapi/sample
    - basic/                      : 各種Serviceのサンプル集です。
    - feature/                    : 広告入稿、ターゲティングなどのサンプル集です。
    - repository/                 : 各種サンプルを利用するための補助ユーティリティです。
    - util/                       : 各種サンプルを利用するための補助ユーティリティです。

## Feature説明

src/main/java/jp/co/yahoo/adssearchapi/sample/feature/
  - AdSample.java                               : 広告の入稿処理のサンプルです。

##  環境設定

Java環境を構築するために、以下をインストールしてください。

1. Java 17 以上
2. Apache Maven 3.5.3、またはそれ以上のバージョン
3. 以下の環境変数を設定します。
  - BASE_ACCOUNT_ID     : x-z-base-account-id ヘッダーで指定するアカウントIDを記述してください(必須)。
  - ACCOUNT_ID          : アカウントIDを記述してください(必須)。
  - CLIENT_ID           : クライアントIDを記述してください(必須)。
  - CLIENT_SECRET       : クライアントシークレットを記述してください(必須)。
  - REFRESH_TOKEN       : リフレッシュトークンを記述してください(必須)。

## 実行

実行例
```
mvn exec:java -Dexec.mainClass=jp.co.yahoo.adssearchapi.sample.basic.reportdefinition.ReportDefinitionServiceSample
```

## ご注意：Yahoo!広告 検索広告 API - サンプルコードの利用に関して

LINEヤフー株式会社の提供するAPIに関するサンプルコードは、別途LINEヤフー株式会社との間で締結いただいた当該APIの提供に関する契約に基づき、APIユーザー様に提供されるものであり、LINEヤフー株式会社との間で当該契約を締結いただいていない場合は、サンプルコードをご利用いただけません。  
また、APIユーザー様に予め通知することなく、サンプルコードの内容や仕様の変更または提供の停止もしくは中止をする場合があります。ご了承のうえご利用ください。  
