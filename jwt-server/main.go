package main

import (
	"fmt"
	"github.com/dgrijalva/jwt-go"
	"net/http"
	"time"
)

var key = []byte("key")

func tokenCheckMiddleware(next http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		tokenString := r.Header.Get("TOKEN")
		if tokenString == "" {
			http.Error(w, "Not authorized", 401)
			return
		}

		token, _ := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
			return key, nil
		})

		if token.Claims.Valid() != nil {
			http.Error(w, "Not authorized", 401)
		} else {
			next.ServeHTTP(w, r)
		}
	}
}

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, World")
}

func tokenHandler(w http.ResponseWriter, r *http.Request) {
	claims := &jwt.StandardClaims{
		ExpiresAt: time.Now().UTC().Add(time.Second * 5).Unix(),
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	signingTokenString, _ := token.SignedString(key)
	fmt.Fprintf(w, signingTokenString)
}

func secretHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Token is Valid")
}

func main() {
	http.HandleFunc("/", handler)
	http.HandleFunc("/token", tokenHandler)
	http.HandleFunc("/secret", tokenCheckMiddleware(secretHandler))
	http.ListenAndServe(":8080", nil)
}
