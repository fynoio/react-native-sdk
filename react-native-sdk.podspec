require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-sdk"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "12.0" }
  s.source       = { :git => "https://github.com/fynoio/react-native-sdk.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,mm,swift}"
  s.dependency "React-Core"
  s.dependency "fyno-push-ios", '~> 3.5.0'
  s.subspec 'fyno-push-ios' do |spec|
    spec.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES' }
  end
end
