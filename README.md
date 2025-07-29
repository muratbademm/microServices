# Mikroservis Projesi - Spring Boot, Kafka, Zipkin ile

## Proje Hakkında
Eureka Service Registry: Servislerin birbirini keşfetmesini sağlar.
Config Server: Merkezi konfigürasyon yönetimi sağlar.
API Gateway: İstekleri ilgili mikroservislere yönlendirir.
lessonService & studentService: İş mantığını içeren mikroservisler.
Kafka: Mikroservisler arası mesajlaşma için.
Zipkin: Dağıtık izleme (distributed tracing) için.

## Proxy ve İletişim Detayları
lessonService içinde StudentClient adında bir proxy interface'i var.

@HttpExchange ve Spring’in HTTP proxy mekanizması ile bu interface’in implementasyonu otomatik oluşturulur.

lessonService bu proxy aracılığıyla studentService'in /student/lesson/{lessonId} endpoint’ine REST çağrısı yapar ve öğrenci listesini JSON olarak alır.

WebClientConfig sınıfında:

LoadBalancedExchangeFilterFunction ile servis ismi studentService Eureka’dan çözülür.

WebClient ve HttpServiceProxyFactory ile StudentClient proxy'si oluşturulur.

Bu sayede REST çağrısı yapıyormuş gibi proxy methodunu çağırarak servisler arası iletişim kolayca yapılır.
## KAFKA
lessonService içinde LessonProducer Kafka mesajı üretmek için kullanıdır.

Kafka mesaj anahtarı Long tipinde, değeri JSON string olarak gönderilir.

kafkaTemplate.sendDefault(id, value) çağrısı ile notification topic’ine mesaj gönderir (default topic notification).

Mesaj gönderme sonuçları CompletableFuture ile asenkron olarak yönetimiş oldu.

Hata veya başarı durumları handleSuccess ve handleFailure metodlarında loglanır.

Kafka, servisler arası gevşek bağlı (event-driven ) iletişim için kullanıldı. 

## Zipkin
Yaml içinde management.tracing.sampling.probability=1.0 ayarıyla tüm isteklerde trace kaydı yapıldı.

Zipkin, Docker container olarak çalıştırıldı ve http://localhost:9411 portundan erişilir.

Spring Cloud Sleuth ve Micrometer Tracing kullanılarak mikroservisler arası çağrılar izlenir.
Bu sayede loglama, metrik toplama, dağıtık trace (izleme) özellikleri bir arada sağlanmış oldu.

Observability: Sistemin iç durumunu dışardan izleyip anlayabilmek için monitoring, logging ve tracing kullanılır.

## Çalışma Akışı 

Kullanıcı API Gateway üzerinden lesson veya student servislerine istek yapar.

API Gateway, Eureka’dan aldığı servis adresleriyle istekleri uygun servise yönlendirir.

lessonService:

Ders verilerini yönetir.

Öğrenci bilgisi almak için StudentClient proxy ile studentService'e REST çağrısı yapar.

Yeni ders eklenince Kafka notification topic’ine mesaj gönderir.

studentService, öğrenci verisini sağlar.

Kafka mesajları başka servisler tarafından dinlenebilir.

Zipkin ile tüm servis çağrıları ve istekler izlenir, performans ve hata analizi yapılabilir.
