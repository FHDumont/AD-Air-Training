package main

import appd "appdynamics"
import (
    "fmt"
    "log"
    "net/http"
    "os"
    "strconv"
    "strings"
    "math/rand"
    "time"
)

var backendName string

func handler(w http.ResponseWriter, r *http.Request) {

    hdr := r.Header.Get(appd.APPD_CORRELATION_HEADER_NAME)
    btHandle := appd.StartBT(r.URL.Path[1:], hdr)

    fmt.Println("handler: " + r.URL.Path[1:])
    fmt.Fprintf(w, "Hi there from handler: " + r.URL.Path[1:] + "!")

    appd.EndBT(btHandle)
}

func cachingServicesHandler(w http.ResponseWriter, r *http.Request) {

    path := r.URL.Path[1:]
    splits := strings.Split(path, "/")
    tier := splits[0]
    action := splits[1]

    fmt.Printf("Calling  %s-%s\n", tier, action)

    hdr := r.Header.Get(appd.APPD_CORRELATION_HEADER_NAME)
    btHandle := appd.StartBT(path, hdr)
    exitCallHandle := appd.StartExitcall(btHandle, "redis")

    fmt.Printf("Calling %s\n", "redis")
    r2 := rand.Intn(50)
    time.Sleep(time.Duration(r2) * time.Millisecond)

    appd.EndExitcall(exitCallHandle)
    appd.EndBT(btHandle)
}

func main() {

    f, err := os.OpenFile("/tmp/logfile.log", os.O_WRONLY|os.O_CREATE|os.O_APPEND, 0644)
    if err != nil {
        log.Fatal(err)
    }
    defer f.Close()
    log.SetOutput(f)
    log.Println("check to make sure it works")

    portNumber, _ := strconv.ParseUint(os.Getenv("APPDYNAMICS_CONTROLLER_PORT"), 10, 16)

    cfg := appd.Config{}
    cfg.AppName = getenvString("APPDYNAMICS_AGENT_APPLICATION_NAME")
    cfg.TierName = getenvString("TIER_NAME")
    cfg.NodeName = getenvString("TIER_NAME") + "-1"
    cfg.Controller.Host = getenvString("APPDYNAMICS_CONTROLLER_HOST_NAME")
    cfg.Controller.Port = uint16(portNumber)
    cfg.Controller.UseSSL =  getenvBool("APPDYNAMICS_CONTROLLER_SSL_ENABLED", false)
    cfg.Controller.Account = getenvString("APPDYNAMICS_AGENT_ACCOUNT_NAME")
    cfg.Controller.AccessKey = getenvString("APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY")
    cfg.InitTimeoutMs = 1000

    appd.InitSDK(&cfg)
    appd.AddBackend("redis", appd.APPD_BACKEND_CACHE, map[string]string{"VENDOR": "Redis"}, true)

    http.HandleFunc("/caching-services/", cachingServicesHandler)
    http.HandleFunc("/search-api/", handler)
    http.ListenAndServe(":8080", nil)
}

func getenvString(key string) (string) {
    stringValue := os.Getenv(key)
    return strings.TrimSpace(stringValue)
}

func getenvBool(key string, defaultValue bool) (bool) {

    stringValue := getenvString(key)
    returnValue := defaultValue

    if (len(stringValue) > 0) {
        boolValue, err := strconv.ParseBool(stringValue)
        if err != nil {
            panic(err)
        }
        returnValue = boolValue
    }
    return returnValue
}