import React from 'react';
import { StyleSheet, View, 
  StatusBar, ListView, TextInput, TouchableOpacity} from 'react-native';

import firebase from 'firebase';

import { Container, Header, 
  Content, ActionSheet, 
  Text, Input, Icon, List, 
  ListItem, Item, Grid } from "native-base";

//import GetNewEntry from'./getNewEntry';
import Modal from 'react-native-modalbox';
import Button from 'react-native-button';

import Expo from 'expo';
import  { createStackNavigator }  from 'react-navigation';
import PriorityMatrix from './PriorityMatrixView';
import AddItemModal from './AddItemModal';
import PresentItem from './PresentItem';

//const pm = require('./PriorityMatrixView');
/*
const NavigationApp = createStackNavigator ({
  Home :  PriorityMatrix, 

  AddItem: AddItemModal, },
  {
    navigationOptions : {
      headerStyle : {
        marginTop : Expo.Constants.statusBarHeight
      }
    }
});*/

const NavigationApp = createStackNavigator ({
  Home: PriorityMatrix,

  AddItem: AddItemModal,

  PresentItem : PresentItem,
})

export default class App extends React.Component {
  render () {
    return <NavigationApp />
  }

}


