export function initialise(
  workspaceId: string,
  integrationID: string,
  userId: string,
  version: string
): Promise<void>;

export function registerPush(
  xiaomiApplicationId: string,
  xiaomiApplicationKey: string,
  pushRegion: string,
  provider: string
): Promise<void>;

export function identifyUser(uniqueID: string, userName: string): Promise<void>;

export function registerInapp(integrationID: string): Promise<void>;

export function mergeProfile(
  oldDistinctId: string,
  newDistinctId: string
): Promise<void>;

export function updateStatus(
  callBackUrl: string,
  status: string
): Promise<void>;

export function resetUser(): Promise<void>;

export function updateName(userName: string): Promise<void>;

export function isFynoNotification(remoteMessage: any): Promise<boolean>;

export function handleFynoNotification(remoteMessage: any): Promise<void>;
