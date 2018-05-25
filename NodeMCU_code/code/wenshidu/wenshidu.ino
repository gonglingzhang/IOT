/* DHTServer - ESP8266 Webserver with a DHT sensor as an input
   Based on ESP8266Webserver, DHTexample, and BlinkWithoutDelay (thank you)
   Version 1.0  5/3/2014  Version 1.0   Mike Barela for Adafruit Industries
*/
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <DHT.h>
#define DHTTYPE DHT11
#define DHTPIN  12  //GPIO12  -  D6
#include <OneWire.h>
#include <DallasTemperature.h>

#define ONE_WIRE_BUS 2 //GPIO2 - D4 
// 初始连接在单总线上的单总线设备
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
float ds18b20_temperature = -999;

//#define SensorPin A0           //pH meter Analog output to nodemcu Analog Input 0
//#define Offset 0.00            //deviation compensate
//#define samplingInterval 20
////#define printInterval 800
//#define ArrayLenth  40    //times of collection
////unsigned long printTime = 0;
//unsigned long samplingTime = 0;
//int pHArray[ArrayLenth];   //Store the average value of the sensor feedback
//int pHArrayIndex = 0;
//float sen0161_ph = -999, sen0161_voltage = -999;

const char* ssid     = "iPhone";
const char* password = "Gong123321";
ESP8266WebServer server(80);

// Initialize DHT sensor
// NOTE: For working with a faster than ATmega328p 16 MHz Arduino chip, like an ESP8266,
// you need to increase the threshold for cycle counts considered a 1 or 0.
// You can do this by passing a 3rd parameter for this threshold.  It's a bit
// of fiddling to find the right value, but in general the faster the CPU the
// higher the value.  The default for a 16mhz AVR is a value of 6.  For an
// Arduino Due that runs at 84mhz a value of 30 works.
// This is for the ESP8266 processor on ESP-01
DHT dht(DHTPIN, DHTTYPE, 11); // 11 works fine for ESP8266
float dht11_humidity = -999, dht11_temperature = -999;  // Values read from sensor
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
  dht.begin();           // initialize temperature sensor
  sensors.begin();
  WiFi.mode(WIFI_STA);
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
  server.on("/dht11-temperature", []() { // if you add this subdirectory to your webserver call, you get text below :)
    get_dht11();       // read sensor
    webString = "DHT11_Temperature:" + String(dht11_temperature); // Arduino has a hard time with float to string
    server.send(200, "text/plain", webString);            // send to someones browser when asked
  });


  server.on("/dht11-humidity", []() { // if you add this subdirectory to your webserver call, you get text below :)
    get_dht11();           // read sensor
    webString = "dht11_humidity:" + String(dht11_humidity);
    server.send(200, "text/plain", webString);               // send to someones browser when asked
  });
//  server.on("/sen0161-ph", []() { // if you add this subdirectory to your webserver call, you get text below :)
//    get_sen0161_ph();           // read sensor
//    webString = "sen0161_voltage:" + String(sen0161_voltage) + "\n" + "sen0161_ph:" + String(sen0161_ph);
//    server.send(200, "text/plain", webString);               // send to someones browser when asked
//  });
  server.begin();
  Serial.println("HTTP server started");
}

void loop(void)
{
//  delay(300);
//  get_dht11();
//  Serial.print(String(dht11_temperature));
//  Serial.println(String(dht11_humidity));
  server.handleClient();
}

//void get_dht11() {
//  // Wait at least 2 seconds seconds between measurements.
//  // if the difference between the current time and last time you read
//  // the sensor is bigger than the interval you set, read the sensor
//  // Works better than delay for things happening elsewhere also
//  unsigned long currentMillis = millis();
//
//  if (currentMillis - previousMillis >= interval) {
//    // save the last time you read the sensor
//    previousMillis = currentMillis;
//
//    // Reading temperature for dht11_humidity takes about 250 milliseconds!
//    // Sensor readings may also be up to 2 seconds 'old' (it's a very slow sensor)
//    dht11_humidity = dht.readHumidity();          // Read dht11_humidity (percent)
//    dht11_temperature = dht.readTemperature();         // Read temperature as Celsius
//    // Check if any reads failed and exit early (to try again).
//    if (isnan(dht11_humidity) || isnan(dht11_temperature)) {
//      Serial.println("Failed to read from DHT11 sensor!");
//      return;
//    }
//  }
//}

void get_sen0161_ph() {
  delay(1000);
  if (millis() - samplingTime > samplingInterval)
  {
    pHArray[pHArrayIndex++] = analogRead(SensorPin);
    if (pHArrayIndex == ArrayLenth)pHArrayIndex = 0;
    sen0161_voltage = 2.5 * avergearray(pHArray, ArrayLenth) * 5.0 / 1024;
    sen0161_ph = 3.5 * sen0161_voltage + Offset;
    samplingTime = millis();
    if (isnan(sen0161_voltage) || isnan(sen0161_ph)) {
      Serial.println("Failed to read from SEN0161 sensor!");
      return;
    }
  }
  /*
    if (millis() - printTime > printInterval)  //Every 800 milliseconds, print a numerical, convert the state of the LED indicator
    {
    Serial.print("Voltage:");
    Serial.print(sen0161_voltage, 2);
    Serial.print("    pH value: ");
    Serial.println(sen0161_ph, 2);
    printTime = millis();
    }
  */
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

