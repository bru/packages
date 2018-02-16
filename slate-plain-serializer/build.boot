(def +lib-version+ "0.5.1")
(def +version+ (str +lib-version+ "-2"))

(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.10.0" :scope "test"]
                 [cljsjs/react            "15.6.2-4"]
                 [cljsjs/react-dom        "15.6.2-4"]
                 [cljsjs/react-dom-server "15.6.2-4"]
                 [cljsjs/slate            "0.32.1-0"]
                 [cljsjs/immutable        "3.8.1-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(task-options!
 pom  {:project     'cljsjs/slate-plain-serializer
       :version     +version+
       :description "A plain text serializer for Slate documents."
       :url         "http://slatejs.org"
       :scm         {:url "https://github.com/ianstormtaylor/slate"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package  []
  (comp
   (download :url (str "https://unpkg.com/slate-plain-serializer@" +lib-version+  "/dist/slate-plain-serializer.js")
             :checksum "1fc61d285e12bc24aa729254219a86f4")
   (download :url (str "https://unpkg.com/slate-plain-serializer@" +lib-version+  "/dist/slate-plain-serializer.min.js")
             :checksum "e7ea17453791443722b795df90129b18")
   (sift :move {#"^slate-plain-serializer.js$"
                "cljsjs/slate-plain-serializer/development/slate-plain-serializer.inc.js"
                #"^slate-plain-serializer.min.js"
                "cljsjs/slate-plain-serializer/production/slate-plain-serializer.min.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.slate-plain-serializer"
              :requires ["cljsjs.react" "cljsjs.react.dom" "cljsjs.react.dom.server" "cljsjs.slate" "cljsjs.immutable"])
   (pom)
   (jar)))
