# Docker-Compose ile test koşmak
 - Docker-compose ile selenium grid üzerinden testleri koşmak için terminalden projenin bulunduğu dizine gidilir.
 - "docker-compose up -d" komutu çalıştırılır.
 - Ardından "mvn clean test -Dbrowser=docker" komutu çalıştırılır.
 
# Test Sonuçlarının Raporlanması
 - Projenin bulunduğu dizinde "mvn clean test -Dbrowser=chrome allure:report" komutu ile testler koşulur.
 - "mvn allure:serve" komutu ile sonuçlara ait rapor görüntülenir.
# Birden fazla tarayıcı desteği
 - Junit Vm Options'a "-Dbrowser=chrome" ya da "-Dbrowser=firefox" parametreleri ile testler farklı tarayıcılar üzerinden koşabilir.

# Notlar 
 - Testlerde kullanılan kullanıcı adı ve şifre alanları için, "src/test/resources/maven.properties" altındaki alanların güncellenmesi gerekmektedir.
 - Testler çalıştırılırken Dbrowser=chrome paremetresi eklenmelidir.
