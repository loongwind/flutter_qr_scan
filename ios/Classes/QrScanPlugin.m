#import "QrScanPlugin.h"
#if __has_include(<qr_scan/qr_scan-Swift.h>)
#import <qr_scan/qr_scan-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "qr_scan-Swift.h"
#endif

@implementation QrScanPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftQrScanPlugin registerWithRegistrar:registrar];
}
@end
