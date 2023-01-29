import 'dart:convert';
import 'package:requests/requests.dart';
import 'package:dio/dio.dart';
import 'package:valorant_client/src/models/storefront.dart';
import 'dart:convert';
import 'package:valorant_client/valorant_client.dart';

Future<List<String>> Client_val(username, pass) async {
  ValorantClient client = ValorantClient(
    UserDetails(
      userName: username,
      password: pass,
      region: Region.eu, // Available regions: na, eu, ap, ko
    ),
    shouldPersistSession: false,
    callback: Callback(
      onError: (String error) {
        print(error);
      },
      onRequestError: (DioError error) {
        print(error.message);
      },
    ),
  );

  await client.init(true);

  /*
  print('Player PUUID => ${client.userPuuid}');

  // TODO: To implement \lib\src\authentication\rso_handler.dart
  // for (var item in client.decodedAccessTokenFields.entries) {
  //  print('${item.key} -> ${item.value}');
  // }


  print('${balance?.valorantPoints} valorant points');
  print('${balance?.radianitePoints} radianite points');

  await Future<void>.delayed(const Duration(seconds: 1));
 
  final currentPlayer = await client.playerInterface.getPlayer();

  print(currentPlayer?.gameName);
  */

  Storefront? storefront = await client.playerInterface.getStorefront();
  var balance = await client.playerInterface.getBalance();

  List<String> skinArry = [];

  if (storefront != null && storefront.skinsPanelLayout != null) {
    for (var item in storefront.skinsPanelLayout!.singleItemOffers) {
      var r = await Requests.get(
          "https://valorant-api.com/v1/weapons/skinlevels/${item}");
      var jj = jsonDecode(r.content());
      skinArry.add(jj['data']['displayIcon']);
    }
  }

  var r = await Requests.get(
      "https://valorant-api.com/v1/bundles/${storefront!.featuredBundle!.bundle!.dataAssetId}");

  var jj = jsonDecode(r.content());
  skinArry.add(jj['data']['displayIcon2']);

  skinArry.add(balance!.radianitePoints.toString());
  skinArry.add(balance.valorantPoints.toString());
  return skinArry;
}

void main() async {
  List<String> tempList = await Client_val("Logoskal", "ManOnTheMoon@123");
}
