(defproject graalvm "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [bidi "2.1.3"]
                 [ring/ring-defaults "0.3.1"]
                 [http-kit "2.3.0"]]

  :aot [graalvm.core]
  :main graalvm.core)

