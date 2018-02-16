(def +lib-version+ "0.11.1")
(def +version+ (str +lib-version+ "-0"))

(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.10.0" :scope "test"]
                 [cljsjs/react "15.6.2-4"]
                 [cljsjs/react-dom "15.6.2-4"]
                 [cljsjs/slate "0.32.1-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(task-options!
 pom  {:project     'cljsjs/slate-react
       :version     +version+
       :description "A completely customizable framework for building rich text editors."
       :url         "http://slatejs.org"
       :scm         {:url "https://github.com/ianstormtaylor/slate"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package  []
  (comp
   (download :url (str "https://unpkg.com/slate-react@" +lib-version+  "/dist/slate-react.js")
             :checksum "7fc2c45e6c10c640e23fa9461f9e5745")
   (download :url (str "https://unpkg.com/slate-react@" +lib-version+  "/dist/slate-react.min.js")
             :checksum "5903d1df90e9cacfd5348213947afff7")
   (sift :move {#"^slate-react.js$"
                "cljsjs/slate-react/development/slate-react.inc.js"
                #"^slate-react.min.js"
                "cljsjs/slate-react/production/slate-react.min.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.slate-react"
              :requires ["cljsjs.react" "cljsjs.react.dom" "cljsjs.slate"])
   (pom)
   (jar)))
