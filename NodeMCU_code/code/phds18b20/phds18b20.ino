/* DHTServer - ESP8266 Webserver with a DHT sensor as an input
   Based on ESP8266Webserver, DHTexample, and BlinkWithoutDelay (thank you)
   Version 1.0  5/3/2014  Version 1.0   Mike Barela for Adafruit Industries
*/
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#define ONE_WIRE_BUS 2 //GPIO2 - D4 
// 初始连接在单总线上的单总线设备
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
float ds18b20_temperature = -999;

#define SensorPin A0           //pH meter Analog output to nodemcu Analog Input 0
#define Offset 0.30            //deviation compensate
#define samplingInterval 20
#define ArrayLenth  40    //times of collection
unsigned long samplingTime = 0;
int pHArray[ArrayLenth];   //Store the average value of the sensor feedback
int pHArrayIndex = 0;
float sen0161_ph = -999, sen0161_voltage = -999;

const char* ssid     = "iPhone";
const char* password = "Gong123321";
ESP8266WebServer server(80);
String webString = "";   // String to display
// Generally, you should use "unsigned long" for variables that hold time
unsigned long previousMillis = 0;        // will store last temp was read
const long interval = 2000;              // interval at which to read sensor

void handle_root() {
  server.send(200, "text/plain", "Hello from ESP8266 Node MCU, read from  /sen0161-ph");
  delay(100);
}
void setup(void)
{
  Serial.println("\n\r \n\rhello");
  // You can open the Arduino IDE Serial Monitor window to see what the code is doing
  Serial.begin(115200);  // Serial connection from ESP-01 via 3.3v console cable
  WiFi.mode(WIFI_STA);
   sensors.begin();
  // Connect to WiFi network
  WiFi.begin(ssid, password);
  Serial.print("\n\r \n\rWorking to connect");
  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  server.on("/", handle_root);
  server.on("/sen0161-ph", []() { // if you add this subdirectory to your webserver call, you get text below :)
    get_sen0161_ph();           // read sensor
    webString = "sen0161_voltage:" + String(sen0161_voltage) + "\n" + "sen0161_ph:" + String(sen0161_ph);
    server.send(200, "text/plain", webString);               // send to someones browser when asked
  });
  server.on("/ds18b20-temperature", []() { // if you add this subdirectory to your webserver call, you get text below :)
    get_ds18b20_temperature();       // read sensor
    webString = "DS18B20_Temperature:" + String(ds18b20_temperature); // Arduino has a hard time with float to string
    server.send(200, "text/plain", webString);            // send to someones browser when asked
  });
  server.begin();
  Serial.println("HTTP server started");
}
void loop(void)
{
  server.handleClient();
}
void get_ds18b20_temperature() {
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;
    sensors.requestTemperatures(); // 发送命令获取温度
    ds18b20_temperature = sensors.getTempCByIndex(0);         // Read temperature as Celsius
    if (isnan(ds18b20_temperature)) {
      Serial.println("Failed to read from ds18b20 sensor!");
      return;
    }
  }
}
void get_sen0161_ph() {
  delay(1000);
  if (millis() - samplingTime > samplingInterval)
  {
    pHArray[pHArrayIndex++] = analogRead(SensorPin);
    if (pHArrayIndex == ArrayLenth)pHArrayIndex = 0;
    sen0161_voltage = 3.6 * avergearray(pHArray, ArrayLenth) * 5.0 / 1024;
    sen0161_ph = 3.5 * sen0161_voltage + Offset;
    samplingTime = millis();
    if (isnan(sen0161_voltage) || isnan(sen0161_ph)) {
      Serial.println("Failed to read from SEN0161 sensor!");
      return;
    }
  }
}
double avergearray(int* arr, int number) {
  int i;
  int max, min;
  double avg;
  long amount = 0;
  if (number <= 0) {
    Serial.println("Error number for the array to avraging!/n");
    return 0;
  }
  if (number < 5) { //less than 5, calculated directly statistics
    for (i = 0; i < number; i++) {
      amount += arr[i];
    }
    avg = amount / number;
    return avg;
  } else {
    if (arr[0] < arr[1]) {
      min = arr[0]; max = arr[1];
    }
    else {
      min = arr[1]; max = arr[0];
    }
    for (i = 2; i < number; i++) {
      if (arr[i] < min) {
        amount += min;      //arr<min
        min = arr[i];
      } else {
        if (arr[i] > max) {
          amount += max;  //arr>max
          max = arr[i];
        } else {
          amount += arr[i]; //min<=arr<=max
        }
      }//if
    }//for
    avg = (double)amount / (number - 2);
  }//if
  return avg;
}

